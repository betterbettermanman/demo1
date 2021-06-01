package com.example.demo1.controller;

import com.example.demo1.bean.ReceiverRequestMsg;
import com.example.demo1.service.CommonService;
import com.example.demo1.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class ReceiverController {
    @Value("${remoteUrl}")
    private String url;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private WeatherService weatherService;

    /* 接受微信消息 */
    @PostMapping("receiver")
    public void receiver(HttpServletRequest request) throws IOException {
        System.out.println("=================");
        BufferedReader reader = request.getReader();
        String str = "";
        String result = "";
        while ((str = reader.readLine()) != null) {
            result = result + str;
        }
        System.out.println(result);
        ReceiverRequestMsg requestMsg = objectMapper.readValue(result, ReceiverRequestMsg.class);// json字符串转实体
        String event = requestMsg.getEvent();
        String person = requestMsg.getFrom_wxid();
        String group = requestMsg.getFrom_wxid();
        switch (event) {
            //群消息
            case "event_group_msg":


                //学习交流群
                if (requestMsg.getFrom_wxid().equals("4930551927@chatroom")) {
                    if (String.valueOf(requestMsg.getMsg()).contains("天气")) {
                        commonService.sendGroup("4930551927@chatroom", weatherService.getWeather(510100));
                    } else {
                        if (requestMsg.getFinal_from_wxid().equals("wxid_delkgc3apxqc22")) {
//                        commonService.sendGroup("4930551927@chatroom", "广哥牛逼");
                        } else if (requestMsg.getFinal_from_wxid().equals("wxid_auq1kbcw4d9x21")) {
                            if (String.valueOf(requestMsg.getMsg()).contains("狗")||String.valueOf(requestMsg.getMsg()).contains("dog")) {
                                commonService.sendGroup("4930551927@chatroom", "龙仔傻逼");
                            }
                        }
                    }
                    //川川得群
                } else if (requestMsg.getFrom_wxid().equals("18508032213@chatroom")) {

                }
                //私聊消息
            case "event_friend_msg":
                if (requestMsg.getFrom_wxid().equals("wxid_r6t23z9oht5t21")) {//喵喵
                    String[] strings = {"猫咪", "我是猫咪"};
                    if (Arrays.asList(strings).contains(requestMsg.getMsg())) {
                        commonService.sendPicture("wxid_r6t23z9oht5t21", "http://localhost:8091/getImage");
                    }
                }
        }
    }

}

