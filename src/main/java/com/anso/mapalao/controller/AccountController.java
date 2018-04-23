package com.anso.mapalao.controller;

import com.anso.mapalao.utils.HttpClientUtil;
import com.anso.mapalao.utils.XmlToBean;
import com.anso.mapalao.vo.Accounts;
import com.anso.mapalao.vo.Loggers;
import com.anso.mapalao.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiumeng
 * @Package com.anso.mapalao.controller
 * @Description:
 * @date 2018 2018/4/23 20:14
 */
public class AccountController {

    protected Logger logger= LoggerFactory.getLogger(AccountController.class);

    public void saveRecord(){
        String ret="";
        String url="http://121.40.190.180/datagate/api/accountapi.ashx";
        Map<String,String> retMap=new HashMap<>();
        retMap.put("Username","anso");
        retMap.put("Password","anso");
        try {
            ret = HttpClientUtil.doGet(url, retMap, "utf-8", null);
            Accounts accounts = XmlToBean.toBean(Accounts.class, ret);
            List<Loggers> loggers = accounts.getAccount().getLoggers();
            List<Message> messagesList=new ArrayList<>();
            for (Loggers logger : loggers) {
                messagesList=logger.getLogger().getMessages().getMessage();
            }
            
            logger.info("获取总记录列表成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new AccountController().saveRecord();
    }
}
