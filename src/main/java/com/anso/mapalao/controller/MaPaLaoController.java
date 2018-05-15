package com.anso.mapalao.controller;

import com.anso.mapalao.entity.Record;
import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.service.RecordService;
import com.anso.mapalao.service.RecordingService;
import com.anso.mapalao.utils.HttpClientUtil;
import com.anso.mapalao.utils.XmlToBean;
import com.anso.mapalao.vo.Recordings;
import com.anso.mapalao.vo.WZ_API_FileDownLoadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/5/15 16:20
 */
@Controller
@RequestMapping("/mapalao")
public class MaPaLaoController {

    protected Logger logger=LoggerFactory.getLogger(MaPaLaoController.class);

    private  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private final String  Username="anso";
    private final String  Password="anso";

    @Autowired
    RecordingService recordingService;

    @Autowired
    RecordService recordService;

    // @Scheduled(cron="0 0 0 1/1 * ? ")
    @RequestMapping("/list")
    public void getRecordData(){

        recording();//16开始
        List<String> numberList = recordingService.getNumberList();
        for (String s : numberList) {
            record(s);
        }
        List<Integer> idList = recordingService.getIdList();
        for (Integer integer : idList) {
            saveAudio(integer);
        }
    }


    public void recording(){
        String ret="";
        String url="http://121.40.190.180/datagate/api/recordingsapi.ashx";
        Map<String,String> retMap=new HashMap<>();
        retMap.put("Username",Username);
        retMap.put("Password",Password);
        retMap.put("startdate",getStartdate());
        retMap.put("enddate",getEnddate());
        try {
            ret = HttpClientUtil.doGet(url, retMap, "utf-8", null);
            Recordings recordings = XmlToBean.toBean(Recordings.class, ret);
            List<Recording> recordingList = recordings.getRecording();
            for (Recording recording : recordingList) {
                recording.setCreateTime(new Date());
            }
            recordingService.batchInsert(recordingList);
            logger.info("获取总记录列表成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public  void record(String number){
        String ret="";
        String url="http://121.40.190.180/hwmonline/hwmcarcgi.cgi";
        Map<String,String> retMap=new HashMap<>();
        retMap.put("user","Anso");
        retMap.put("pass","anso");
        retMap.put("logger",number);
        retMap.put("period","4");
        retMap.put("startdate",getStartdate());
        retMap.put("enddate",getEnddate());
        retMap.put("export","csv");
        retMap.put("ConsumptionValues","1");
        List<Record> recordList =new ArrayList<>();
        try {
            ret = HttpClientUtil.doGet(url, retMap, "utf-8", null);
            String[] str = ret.split("\n");
            String[] newStr=new String[str.length-1];
            System.arraycopy(str, 1, newStr, 0, newStr.length);
            for (String s : newStr) {
                String[] split = s.split(",");
                Record record=new Record();
                record.setDateTime(split[0]);
                record.setC1Leak(split[1]);
                record.setC2Noise(split[2]);
                record.setC3Spread(split[3]);
                record.setCreateTime(new Date());
                recordList.add(record);
            }
            recordService.batchInsert(recordList);
            logger.info("获取详细数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${filepath}")
    private String filePath;

    public void saveAudio(Integer id){
        String url="http://121.40.190.180/datagate/api/getrecordingsapi.ashx";
        Map<String,String> reqMap=new HashMap<>();
        reqMap.put("Username","anso");
        reqMap.put("Password","anso");
        reqMap.put("ID",String.valueOf(id));
        WZ_API_FileDownLoadResponse result=null;
        filePath="W://111";
        try {
            result=HttpClientUtil.DownloadFile(reqMap,url,filePath,null);
            logger.info("下载文件成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 获取今天日期
     * @return
     */
    public String getStartdate(){
        Calendar ca = Calendar.getInstance();
        String startdate=format.format(ca.getTime());
        return startdate;
    }

    /**
     *
     * 获取2月前
     * @return
     */
    public String getEnddate(){
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, -2); // 年份减1
        String enddate=format.format(ca.getTime());
        return enddate;
    }

    public static void main(String[] args){
     new MaPaLaoController().recording();
    }

}
