package com.example.demo1.controller;

import com.example.demo1.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private CommonService commonService;

    /* 发送消息 */
    @GetMapping("send")
    public void Send(String wxid, String msg) {
        commonService.sendInfo(wxid, msg);
    }

    /* 发送图片 */
    @GetMapping("sendPicture")
    public void sendPicture(String wxid, String pictureUrl) {
        commonService.sendPicture(wxid, pictureUrl);
    }

    @GetMapping("getWxid")
    public String getWxid() {
        return "";
    }
}


