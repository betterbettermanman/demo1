package com.example.demo1.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo1.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private AppProperties appProperties;

    public void test(String wxid, String msg) {
        Assert.notBlank(wxid, "wxid should be provided");
        Assert.notBlank(msg, "message should be provided");
        if (StrUtil.equalsIgnoreCase(wxid, appProperties.getRobotId())) {
            return;
        }
        if (StrUtil.containsIgnoreCase(StrUtil.trim(msg), "帅")) {
            commonService.sendInfo(wxid, getContent());
        }
        if (StrUtil.containsIgnoreCase(StrUtil.trim(msg), "菜")) {
            commonService.sendInfo(wxid, "我是菜");
        }
    }


    private static String getContent() {
        int random = NumberUtil.generateRandomNumber(1, 1000, 1)[0];
        if (random / 2 == 0) {
            return "龙仔太帅了";
        } else {
            return "广仔太帅了";
        }
    }
}
