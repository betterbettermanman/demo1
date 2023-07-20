package com.example.demo1.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.bean.ReceiverRequestMsg;
import com.example.demo1.config.AppProperties;
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

    /**
     * 接受微信消息
     *
     * @param request
     * @throws Exception
     */
    @PostMapping("receiver")
    public void receiver(HttpServletRequest request) throws Exception {
        ReceiverRequestMsg requestMsg = parseRequest(request);
        if (requestMsg == null) {
            return;
        }
        String event = requestMsg.getEvent();
        String msg = requestMsg.getMsg();
        switch (event) {
            //群消息(收到)
            case "EventGroupMsg":
                System.out.println("群消息");
                yogaService.test(requestMsg.getFrom_wxid(), msg);
                break;
            //私聊消息（收到）
            case "EventFriendMsg":
                System.out.println("私聊消息");
                method1(requestMsg.getFrom_wxid(), msg);
                method2(requestMsg.getFrom_wxid(), msg);
                break;
            case "EventReceivedTransfer":
                System.out.println("转账事件");
                break;

            case "EventFriendVerify":
                System.out.println("好友请求事件");
                break;
            case "EventGroupMemberAdd":
                System.out.println("群成员增加事件");
                break;

            case "EventGroupMemberDecrease":
                System.out.println("群成员减少事件");
                break;
            case "EventSysMsg":
                System.out.println("系统消息事件");
                break;
            case "EventLogin":
                System.out.println("账号登录成功/下线时");
                break;
            case "EventSendOutMsg":
                System.out.println("发送消息事件");
                break;
            default:
                System.out.println("其他未知事件");

        }
        //公共处理逻辑
        if (requestMsg.getType().equals("1")) {
            parseWeather(requestMsg.getFrom_wxid(), msg);
            createQr(requestMsg.getFrom_wxid(), msg);
            createSql(requestMsg.getFrom_wxid(), msg);
            //识别图片二维码
        } else if (requestMsg.getType().equals("3")) {
            parseQr(requestMsg.getFrom_wxid(), msg);
        }
    }

    /**
     * 解析微信接受到得消息
     *
     * @param request
     * @return
     * @throws IOException
     */
    private ReceiverRequestMsg parseRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();
        String str = "";
        String result = "";
        while ((str = reader.readLine()) != null) {
            result = result + str;
        }
        String s = result.replaceAll("\\\\", "\\\\\\\\");
        if (s.contains(myAppProperties.getRobotId())) {
            System.out.println("=================");
            System.out.println(s);
        } else {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(jsonObject);
        String type = jsonObject.getString("type");
        //1/文本消息 3/图片消息 34/语音消息  42/名片消息  43/视频 47/动态表情 48/地理位置  49/分享链接  2000/转账 2001/红包  2002/小程序  2003/群邀请
        switch (type) {
            case "1":
                System.out.println("文本消息");
                break;
            case "3":
                System.out.println("图片消息");
                break;
            case "34":
                System.out.println("语音消息");
                break;
            case "42":
                System.out.println("名片消息");
                break;
            case "43":
                System.out.println("视频");
                break;
            case "47":
                System.out.println("动态表情");
                break;
            case "49":
                System.out.println("分享链接");
                break;
            case "2000":
                System.out.println("转账");
                break;
            case "2001":
                System.out.println("红包");
                break;
            case "2002":
                System.out.println("小程序");
                break;
            case "2003":
                System.out.println("群邀请");
                break;
            default:
                System.out.println("未知消息类型【" + type + "】");
                break;
        }
        // 过滤当前配置账号的消息
        ReceiverRequestMsg receiverRequestMsg = JSON.parseObject(s, ReceiverRequestMsg.class);
        if (receiverRequestMsg.getRobot_wxid().equals(myAppProperties.getRobotId())) {
            return receiverRequestMsg;
        }
        return null;
    }

    /**
     * 解析二维码图片
     *
     * @param wxid
     * @param msg
     */
    private void parseQr(String wxid, String msg) {
        String qrMsg = QrCodeUtil.decode(msg);
        if (null != qrMsg) {
            commonService.sendInfo(wxid, qrMsg);
        }
    }


    /**
     * 创建二维码图片
     *
     * @param wxid
     * @param msg
     * @throws Exception
     */
    private void createQr(String wxid, String msg) throws Exception {
        if (msg.endsWith("&二维码")) {
            // 图片名称
            String fileName = System.currentTimeMillis() + ".jpg";
            // 存放在二维码中的内容
            // 输出二维码的文件流
            FileOutputStream qrCodeOutputStream = new FileOutputStream(myAppProperties.getPictureQrPath() + File.separator + fileName);
            //生成二维码
            QrCodeUtil.createImage(msg, qrCodeOutputStream, "JPG");
            qrCodeOutputStream.flush();
            //发送图片
            commonService.sendPicture(wxid, myAppProperties.getPictureServer() + "/getImage2?fileName=" + fileName);
        }
    }

    /**
     * 解析天气
     *
     * @param wxid
     * @param msg
     */
    public void parseWeather(String wxid, String msg) {
        if (msg.endsWith("天气")) {
            String cityName = msg.substring(0, msg.indexOf("天气"));
            String weather = weatherService.getWeather(cityName);
            if (weather != null) {
                commonService.sendInfo(wxid, weather);
            }
        }
    }

    /**
     * 生成sql语句
     *
     * @param wxid
     * @param msg
     */
    public void createSql(String wxid, String msg) {
        if (msg.endsWith("&sql")) {
            msg = msg.substring(0, msg.indexOf("&sql"));
            if (msg.split(",").length == 2) {
                commonService.sendInfo(wxid, sqlService.createSql(msg.split(",")[0], msg.split(",")[1]));
            }

        }
    }

    /**
     * 喵喵专属
     *
     * @param wxid
     * @param msg
     */
    public void method1(String wxid, String msg) {
        if (wxid.equals("wxid_r6t23z9oht5t21") || wxid.equals("wxid_uyu8cpztrem522")) {
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

    /**
     * 菜专属
     *
     * @param wxid
     * @param msg
     */
    public void method2(String wxid, String msg) {
        if (wxid.equals("wxid_uyu8cpztrem522")) {
            if ("测试".equals(msg.trim())) {
                commonService.sendInfo(wxid, "机器运行中");
            } else if ("测试图片".equals(msg.trim())) {
                commonService.sendPicture(wxid, "机器运行中");
            }
        }
    }

    /**
     * 处理需要邮件发送的问题
     *
     * @param msg
     */
    public void method3(String msg) {

    }
}


