package com.anso.mapalao.controller;

import com.anso.mapalao.service.RecordingService;
import com.anso.mapalao.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/4/24 8:25
 */
public class RetrieveRecordingsController {
    protected Logger logger=LoggerFactory.getLogger(RetrieveRecordingsController.class);

    @Value("${filepath}")
    private String filePath;

    @Autowired
    RecordingService recordingService;

    @Scheduled(cron="0 0 0 1/2 * ? ")
    // @GetMapping("/list")
    public void saveRecord(String id){
        String ret="";
        String url="http://121.40.190.180/datagate/api/getrecordingsapi.ashx";
        Map<String,String> reqMap=new HashMap<>();
        reqMap.put("Username","anso");
        reqMap.put("Password","anso");
        reqMap.put("ID",id);
        try {
            // HttpClientUtil.DownloadFile(reqMap,url,"W:\\111",null);
            HttpClientUtil.DownloadFile(reqMap,url,filePath,null);
            logger.info("获取声音数据成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new RetrieveRecordingsController().saveRecord("14375");
    }
}
