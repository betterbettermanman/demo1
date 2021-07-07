package com.example.demo1.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo1.bean.ReceiverRequestMsg;
import com.example.demo1.config.AppProperties;
import com.example.demo1.config.WxIdProperties;
import com.example.demo1.service.CommonService;
import com.example.demo1.service.SqlService;
import com.example.demo1.service.WeatherService;
import com.example.demo1.service.YogaService;
import com.example.demo1.util.QrCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class ReceiverController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private AppProperties myAppProperties;
    @Autowired
    private SqlService sqlService;
    @Autowired
    private YogaService yogaService;
    @Autowired
    private WxIdProperties wxIdProperties;

    /* 接受微信消息 */
    @PostMapping("receiver")
    public void receiver(HttpServletRequest request) throws Exception {
        ReceiverRequestMsg requestMsg = parseRequest(request);
        String event = requestMsg.getEvent();
        String msg = requestMsg.getMsg().toString();
        switch (event) {
            case "EventGroupMsg":                 //群消息
                yogaService.test(requestMsg.getFrom_wxid(), msg);
                break;
            case "EventFriendMsg":                //私聊消息
                method1(requestMsg.getFrom_wxid(), msg);
                method2(requestMsg.getFrom_wxid(), msg);
                break;
        }
        //公共处理逻辑
        if (requestMsg.getType().equals("1")) {
            parseWeather(requestMsg.getFrom_wxid(), msg);
            createQr(requestMsg.getFrom_wxid(), msg);
            createSql(requestMsg.getFrom_wxid(), msg);
        } else if (requestMsg.getType().equals("3")) {//识别图片二维码
            parseQr(requestMsg.getFrom_wxid(), msg);
        }
    }

    /* 解析微信接受到得消息 */
    private ReceiverRequestMsg parseRequest(HttpServletRequest request) throws IOException {
        System.out.println("=================");
        BufferedReader reader = request.getReader();
        String str = "";
        String result = "";
        while ((str = reader.readLine()) != null) {
            result = result + str;
        }
        System.out.println(result);
        ReceiverRequestMsg requestMsg = objectMapper.readValue(result, ReceiverRequestMsg.class);// json字符串转实体
        return requestMsg;
    }

    /* 解析二维码图片 */
    public void parseQr(String wxid, String msg) {
        String QrMsg = QrCodeUtil.decode(msg);
        if (null != QrMsg) {
            commonService.sendInfo(wxid, QrMsg);
        }
    }


    /* 创建二维码图片 */
    public void createQr(String wxid, String msg) throws Exception {
        if (msg.endsWith("&二维码")) {
            // 图片名称
            String fileName = System.currentTimeMillis() + ".jpg";
            // 存放在二维码中的内容
            String text = msg;
            // 输出二维码的文件流
            FileOutputStream qrCodeOutputStream = new FileOutputStream(myAppProperties.getPictureQrPath() + File.separator + fileName);
            //生成二维码
            QrCodeUtil.createImage(text, qrCodeOutputStream, "JPG");
            qrCodeOutputStream.flush();
            //发送图片
            commonService.sendPicture(wxid, myAppProperties.getPictureServer() + "/getImage2?fileName=" + fileName);
        }
    }

    /* 解析天气 */
    public void parseWeather(String wxid, String msg) {
        if (msg.endsWith("天气")) {
            String cityName = msg.substring(0, msg.indexOf("天气"));
            String weather = weatherService.getWeather(cityName);
            if (weather != null) {
                commonService.sendInfo(wxid, weather);
            }
        }
    }

    //生成sql语句
    public void createSql(String wxid, String msg) {
        if (msg.endsWith("&sql")) {
            msg = msg.substring(0, msg.indexOf("&sql"));
            if (msg.split(",").length == 2) {
                commonService.sendInfo(wxid, sqlService.createSql(msg.split(",")[0], msg.split(",")[1]));
            }

        }
    }

    //喵喵专属
    public void method1(String wxid, String msg) {
        if (wxid.equals("wxid_r6t23z9oht5t21") || StrUtil.equalsIgnoreCase(wxIdProperties.getStudyGroup(), wxid)) {
            String[] strings = {"猫咪", "我是猫咪"};
            if ("我是不是乖猫咪".equals(msg.trim())) {
                commonService.sendInfo(wxid, "你是乖猫咪");
                commonService.sendPicture(wxid, "http://localhost:8091/getImage");
            } else if ("你在爪子".equals(msg.trim()) || "你抓".equals(msg.trim())) {
                commonService.sendInfo(wxid, "我在吃屎");
            } else if (Arrays.asList(strings).contains(msg)) {
                commonService.sendPicture(wxid, "http://localhost:8091/getImage");
            }
        }
    }

    //菜专属
    public void method2(String wxid, String msg) {
        if (wxid.equals("wxid_uyu8cpztrem522")) {
            if ("测试".equals(msg.trim())) {
                commonService.sendInfo(wxid, "机器运行中");
            }
        }
    }
}


