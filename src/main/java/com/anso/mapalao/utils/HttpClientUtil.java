package com.anso.mapalao.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 */

public class HttpClientUtil {


    private static int connectTimeout = 10000, soTimeout = 60000;
    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;

    static {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } }, null);

            // 域名验证 这种方式不对主机名进行验证，验证功能被关闭
            //SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf).build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();

            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String doGet(String url, Map<String, String> params, String charset,HashMap<String, String> headMap) throws Exception {
        return doGet(url, params, charset, connectTimeout, soTimeout,headMap);
    }

    public static String doPost(String url, Map<String, String> params, String charset,Map<String, String> headMap) throws Exception {
        return doPost(url, params, charset, connectTimeout, soTimeout,headMap);
    }
    public static String doPostWithJson(String url, String json, String charset,Map<String, String> headMap) throws Exception {
        return doPostWithJson(url, json, charset, connectTimeout, soTimeout,headMap);
    }
    public static String postFile(File file,String url, Map<String, String> params, String charset,Map<String, String> headMap) throws Exception {
        return postFile(file,url, params, charset, connectTimeout, soTimeout,headMap);
    }

    /**
     *
     * HTTP Get 获取内容
     *
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     * @throws Exception
     *
     */

    public static String doGet(String url, Map<String, String> params, String charset, int connectTimeout, int socketTimeout,HashMap<String, String> headMap) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }
        HttpGet httpGet = new HttpGet(url);
        setGetHead(httpGet,headMap);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }
        EntityUtils.consume(entity);
        response.close();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+result);
        }
        return result;

    }

    /**
     *
     * HTTP Post 获取内容
     *
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     * @throws Exception
     *
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int socketTimeout,Map<String, String> headMap) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        setPostHead(httpPost,headMap);
        httpPost.setConfig(requestConfig);
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }
        EntityUtils.consume(entity);
        response.close();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+result);
        }
        return result;
    }
    public static String doPostWithJson(String url, String json, String charset, int connectTimeout, int socketTimeout,Map<String, String> headMap) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        HttpPost httpPost = new HttpPost(url);
        setPostHead(httpPost,headMap);
        httpPost.setConfig(requestConfig);
        StringEntity requestEntity = new StringEntity(json,charset);
        requestEntity.setContentEncoding(charset);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }
        EntityUtils.consume(entity);
        response.close();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+result);
        }
        return result;
    }

    /**
     * 发送post数据流
     *
     * @param url
     *            地址
     * @param b
     *            字节流，从字符串转byte时需要 指定编码转换
     * @param connectTimeout
     *            链接超时时间(毫秒)
     * @param soTimeout 读取超时时间
     *            (毫秒)
     * @return   接收到的byte 需要指定编码转换
     * @throws Exception
     */
    public static byte[] doPostStream(String url, byte[] b, Map<String, String> headmap, int connectTimeout, int soTimeout) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        // 请求处理页面
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);

        if (headmap != null) {
            for (String name : headmap.keySet()) {
                String value = headmap.get(name);
                httppost.setHeader(name, value);
            }
        }

        // 创建待处理的文件
        // 创建待处理的表单域内容文本
        ByteArrayEntity bae = new ByteArrayEntity(b);
        // 设置请求
        httppost.setEntity(bae);
        // 执行
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        byte[] result = null;
        if (entity != null) {
            result = EntityUtils.toByteArray(entity);
        }
        EntityUtils.consume(entity);
        response.close();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httppost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+new String(result,"UTF-8"));
        }
        return result;

    }



    /**
     * 发送post数据流
     * @param url
     * @param stream
     * @param headmap
     * @param connectTimeout
     * @param soTimeout
     * @return  接收到的byte 需要指定编码转换
     * @throws Exception
     */
    public static byte[] doPostInputStream(String url, InputStream stream, Map<String, String> headmap, int connectTimeout, int soTimeout) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        // 请求处理页面
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);

        if (headmap != null) {
            for (String name : headmap.keySet()) {
                String value = headmap.get(name);
                httppost.setHeader(name, value);
            }
        }
        // 创建待处理的文件
        // 创建待处理的表单域内容文本
        InputStreamEntity bae = new InputStreamEntity(stream);
        // 设置请求
        httppost.setEntity(bae);
        // 执行
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        byte[] result = null;
        if (entity != null) {
            result = EntityUtils.toByteArray(entity);
        }
        EntityUtils.consume(entity);
        response.close();

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httppost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+new String(result,"UTF-8"));
        }
        return result;

    }


    /**
     * 发送文件对象到服务端
     * @param file
     * @param url
     * @param params
     * @param charset
     * @param connectTimeout
     * @param socketTimeout
     * @return
     */
    public static String postFile( File file,String url, Map<String, String> params, String charset, int connectTimeout, int socketTimeout,Map<String, String> headMap) {

        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }

            HttpPost httpPost = new HttpPost(url);
            setPostHead(httpPost,headMap);
            httpPost.setConfig(requestConfig);
            //将java.io.File对象添加到HttpEntity（org.apache.http.HttpEntity）对象中
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //解决上传文件，文件名中文乱码问题
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
            // builder.addBinaryBody("uploadFile", file, ContentType.create("multipart/form-data"), "file");
            builder.addPart("file", new FileBody(file));
            httpPost.setEntity(builder.build());
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result =EntityUtils.toString(resEntity);
                // 消耗掉response
                EntityUtils.consume(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送文件流到服务端
     * @param inputStream
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String postFile(InputStream inputStream, String url, Map<String, String> params, String charset) {

        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs));
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            // FileBody binFileBody = new FileBody(new File(inputStream));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
            //addBinaryBody方法直接可以添加File、InputStream、byte[]类型的数据
            builder.addBinaryBody("uploadFile", inputStream, ContentType.create("multipart/form-data"), "text01.txt");
            // StringBody stringBody = new StringBody("12",ContentType.MULTIPART_FORM_DATA);
            //addPart方法只能添加ContentBody类型的数据 FileBody、InputStreamBody、StringBody
            // builder.addPart("id",stringBody);
            httpPost.setEntity(builder.build());
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result =EntityUtils.toString(resEntity);
                // 消耗掉response
                EntityUtils.consume(resEntity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }



    /**
     * 设置http的HEAD
     *
     * @param httpGet
     * @param headMap
     */
    private static void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpPost
     * @param headMap
     */
    private static void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headMap.get(key));
            }
        }
    }

    public static String postWithHead(String url, Map<String, String> params, String charset,Map<String, String> headmap) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        List<NameValuePair> pairs = null;

        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setPostHead(httpPost, headmap);
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }
        EntityUtils.consume(entity);
        response.close();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode+","+result);
        }
        return result;
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }



    public static void main(String[] args) throws Exception {
        String url="http://www.baidu.com/";
        Map head=new HashMap();
        head.put("Content-Type", "text/xml; charset=GBK");
    }
}
