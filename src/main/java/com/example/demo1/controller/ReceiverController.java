package com.example.demo1.controller;

import com.example.demo1.bean.ReceiverRequestMsg;
import com.example.demo1.service.CommonService;
import com.example.demo1.service.WeatherService;
import com.example.demo1.util.QrCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public void receiver(HttpServletRequest request) throws Exception {
        ReceiverRequestMsg requestMsg = parseRequest(request);
        String event = requestMsg.getEvent();
        String msg = requestMsg.getMsg().toString();
        switch (event) {
            case "event_group_msg":                 //群消息
                break;
            case "event_friend_msg":                //私聊消息
                break;
        }
        //公共处理逻辑
        if (requestMsg.getType().equals("1")) {
            parseWeather(requestMsg.getFrom_wxid(), msg);
            createQr(requestMsg.getFrom_wxid(), msg);
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

    @Value("${pictureQrPath}")
    private String pictureQrPath;

    /* 创建二维码图片 */
    public void createQr(String wxid, String msg) throws Exception {
        if (msg.endsWith("&二维码")) {
            // 图片名称
            String fileName = System.currentTimeMillis() + ".jpg";
            // 存放在二维码中的内容
            String text = msg;
            // 输出二维码的文件流
            FileOutputStream qrCodeOutputStream = new FileOutputStream(pictureQrPath + File.separator+fileName);
            //生成二维码
            QrCodeUtil.createImage(text, qrCodeOutputStream, "JPG");
            qrCodeOutputStream.flush();
            //发送图片
            commonService.sendPicture(wxid, "http://localhost:8091/getImage2?fileName=" + fileName);
        }
    }

    /* 解析天气 */
    public void parseWeather(String wxid, String msg) {
        if (msg.endsWith("天气")) {
            String cityName = msg.substring(0, msg.indexOf("天气"));
            String weather = weatherService.getWeather(cityName);
            if (weather != null) {
                commonService.sendGroup(wxid, weather);
            }
        }
    }

    //学习交流群
    public void method() {
       /* if (requestMsg.getFrom_wxid().equals("4930551927@chatroom")) {
            if (requestMsg.getFinal_from_wxid().equals("wxid_delkgc3apxqc22")) {
                if (String.valueOf(requestMsg.getMsg()).contains("狗") || String.valueOf(requestMsg.getMsg()).contains("dog")) {
                    commonService.sendGroup("4930551927@chatroom", "广仔傻逼");
                }
            } else if (requestMsg.getFinal_from_wxid().equals("wxid_auq1kbcw4d9x21")) {
                if (String.valueOf(requestMsg.getMsg()).contains("狗") || String.valueOf(requestMsg.getMsg()).contains("dog")) {
                    commonService.sendGroup("4930551927@chatroom", "龙仔傻逼");
                }
            }
        }*/

    }

    public void method1() {
        /*if (requestMsg.getFrom_wxid().equals("wxid_r6t23z9oht5t21")) {//喵喵专属
            String[] strings = {"猫咪", "我是猫咪"};
            if (Arrays.asList(strings).contains(msg)) {
                commonService.sendPicture("wxid_r6t23z9oht5t21", "http://localhost:8091/getImage");
            }
        } else if (requestMsg.getFrom_wxid().equals("wxid_uyu8cpztrem522")) {//验证当前机器是否正常
            commonService.sendInfo("wxid_uyu8cpztrem522", "机器正在运行中");
        }*/
    }
}


