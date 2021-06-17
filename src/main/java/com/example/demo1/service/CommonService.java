package com.example.demo1.service;

import com.example.demo1.config.AppProperties;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommonService {
    @Autowired
    private AppProperties myAppProperties;
    @Autowired
    private RestTemplate restTemplate;

    //发送消息到微信群
    public void sendGroup(String to_wxid, String msg) {
        JSONObject requestMsg = createMsg();
        requestMsg.put("type", "send_text_msg");
        requestMsg.put("to_wxid", to_wxid);//群id
        requestMsg.put("msg", msg);//信息
        sendInfo(requestMsg);
    }

    //发送消息到个人
    public void sendPerson(String to_wxid, String msg) {
        JSONObject requestMsg = createMsg();
        requestMsg.put("type", "send_text_msg");
        requestMsg.put("to_wxid", to_wxid);//个人id
        requestMsg.put("msg", msg);//信息
        sendInfo(requestMsg);
    }

    /**
     * 发送消息
     *
     * @param wxId 目标人id，或者目标群id
     * @param msg  消息
     */
    public void sendInfo(String wxId, String msg) {
        JSONObject requestMsg = createMsg();
        requestMsg.put("type", "send_text_msg");
        requestMsg.put("to_wxid", wxId);//个人id
        requestMsg.put("msg", msg);//信息
        sendInfo(requestMsg);
    }

    /**
     * 发送图片
     *
     * @param wxId    目标人id或者目标群id
     * @param picture 图片URL
     */
    public void sendPicture(String wxId, String picture) {
        JSONObject requestMsg = createMsg();
        requestMsg.put("type", "send_image_msg");
        requestMsg.put("to_wxid", wxId);//个人id
        requestMsg.put("path", picture);//信息
        sendInfo(requestMsg);
    }

    //创建消息体
    public JSONObject createMsg() {
        JSONObject msg = new JSONObject();
        msg.put("robot_wxid", myAppProperties.getRobotId());
        return msg;
    }

    //发送消息
    public void sendInfo(JSONObject msg) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity formEntity = new HttpEntity(msg, headers);
        restTemplate.postForEntity(myAppProperties.getRemoteUrl(), formEntity, String.class);
    }

}
