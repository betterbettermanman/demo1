package com.example.demo1.service;

import com.example.demo1.config.AppProperties;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CommonService {
    @Autowired
    private AppProperties myAppProperties;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送消息
     *
     * @param wxId 目标人id，或者目标群id
     * @param msg  消息
     */
    public void sendInfo(String wxId, String msg) {
        JSONObject requestMsg = createMsg();
        requestMsg.put("event", "SendTextMsg");
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
        requestMsg.put("event", "SendImageMsg");
        requestMsg.put("to_wxid", wxId);//个人id
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url",picture);
        jsonObject.put("name", UUID.randomUUID().toString());
        requestMsg.put("msg", jsonObject.toString());//信息
        sendInfo(requestMsg);
    }

    //创建消息体
    public JSONObject createMsg() {
        JSONObject msg = new JSONObject();
        msg.put("robot_wxid", myAppProperties.getRobotId());
        return msg;
    }

    //发送消息
    private void sendInfo(JSONObject msg) {
        MediaType type = MediaType.parseMediaType("application/json; charset=GBK");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.add("Accept","application/json; charset=GBK");
        HttpEntity formEntity = new HttpEntity(msg.toString(), headers);
        restTemplate.postForEntity(myAppProperties.getRemoteUrl(), formEntity, String.class);
    }

}
