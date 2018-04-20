package com.anso.mapalao.controller;

import com.anso.mapalao.entity.Recording;
import com.anso.mapalao.service.RecordingService;
import com.anso.mapalao.utils.HttpClientUtil;
import com.anso.mapalao.utils.XmlToBean;
import com.anso.mapalao.vo.Recordings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/4/19 21:54
 */
@Component
// @Controller
// @RequestMapping("/recording")

public class RecordingController {

    protected Logger logger=LoggerFactory.getLogger(RecordController.class);

    @Autowired
    RecordingService recordingService;

    @Scheduled(cron="0 0 0 1/2 * ? ")
    // @GetMapping("/list")
    public void saveRecord(){
        String ret="";
        String url="http://121.40.190.180/datagate/api/recordingsapi.ashx";
        Map<String,String> retMap=new HashMap<>();
        retMap.put("Username","anso");
        retMap.put("Password","anso");
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
    public static void main(String[] args){
        new RecordingController().saveRecord();
    }
}
