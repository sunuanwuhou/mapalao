package com.anso.mapalao.controller;

import com.anso.mapalao.utils.HttpClientUtil;
import com.anso.mapalao.vo.WZ_API_FileDownLoadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/4/23 21:10
 */
@RequestMapping("/retrieveRecord")
@Controller
public class RetrieveRecordingsController {

    protected Logger logger= LoggerFactory.getLogger(RetrieveRecordingsController.class);

    @RequestMapping("/saveAutio")
    public void saveAudio(String id){
        String filePath="F:\\1111\\";
        String url="http://121.40.190.180/datagate/api/getrecordingsapi.ashx";
        Map<String,String> reqMap=new HashMap<>();
        reqMap.put("Username","anso");
        reqMap.put("Password","anso");
        reqMap.put("ID",id);
        WZ_API_FileDownLoadResponse result=null;
        try {
            result=HttpClientUtil.DownloadFile(reqMap,url,filePath,null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new RetrieveRecordingsController().saveAudio("14375");
    }
}
