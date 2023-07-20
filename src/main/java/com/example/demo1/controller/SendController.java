package com.example.demo1.controller;

import com.example.demo1.service.CommonService;
import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
public class SendController {
    @Resource
    private CommonService commonService;

    /**
     * 发送文本消息
     *
     * @param wxid
     * @param msg
     */
    @GetMapping("sendTextMsg")
    public void sendTextMsg(String wxid, String msg) {
        commonService.sendInfo(wxid, msg);
    }

    /**
     * 发送图片
     *
     * @param wxid
     * @param pictureUrl
     */
    @GetMapping("sendImageMsg")
    public void sendImageMsg(String wxid, String pictureUrl) {
        commonService.sendPicture(wxid, pictureUrl);
    }

    @GetMapping("getWxid")
    public String getWxid() {
        commonService.sendInfo("wxid_sbpp4a752raj22", weatherService.getWeather(510182));
        return "";
    }
    @Autowired
    private WeatherService weatherService;
}


