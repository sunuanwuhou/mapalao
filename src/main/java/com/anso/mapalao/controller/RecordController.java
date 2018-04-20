package com.anso.mapalao.controller;

import com.anso.mapalao.entity.Record;
import com.anso.mapalao.service.RecordService;
import com.anso.mapalao.service.RecordingService;
import com.anso.mapalao.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/4/19 23:22
 */
@Component

// @Controller
// @RequestMapping("/record")
public class RecordController {

    protected Logger logger=LoggerFactory.getLogger(RecordController.class);


    @Autowired
    RecordService recordService;
    @Autowired
    RecordingService recordingService;


    @Scheduled(cron="0 0 0 1/2 * ?  ")
    // @Scheduled(cron="0/30 * * * * ?  ")
    // @RequestMapping("/list")
    public  void saveRecord(){
        String ret="";
        String url="http://121.40.190.180/hwmonline/hwmcarcgi.cgi";
        Map<String,String> retMap=new HashMap<>();
        retMap.put("user","Anso");
        retMap.put("pass","anso");
        retMap.put("logger","1064887202666");
        retMap.put("period","4");
        retMap.put("startdate","2017-06-01");
        retMap.put("enddate","2017-06-27");
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

    public static void main(String[] args){

    }
}
