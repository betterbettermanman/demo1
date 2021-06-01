package com.example.demo1.controller;

import com.example.demo1.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private CommonService commonService;

    /* 发送消息 */
    @PostMapping("send")
    public void Send() {
        commonService.sendPicture("4930551927@chatroom", "http://localhost:8091/getImage");
    }

}


