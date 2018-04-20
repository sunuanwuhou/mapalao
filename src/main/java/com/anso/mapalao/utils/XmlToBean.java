package com.anso.mapalao.utils;


import com.thoughtworks.xstream.XStream;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.utils
 * @Description:
 * @date 2018 2018/4/19 22:17
 */
public class XmlToBean {

    public static <T> T toBean(Class<T> clazz, String xml) {
        try {
            XStream xstream = new XStream();
            xstream.setClassLoader(clazz.getClassLoader());
            //声明XStream注解来源
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);
            T obj = (T) xstream.fromXML(xml);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
