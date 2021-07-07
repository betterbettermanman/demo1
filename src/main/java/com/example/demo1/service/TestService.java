package com.example.demo1.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo1.config.WxIdProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private WxIdProperties wxIdProperties;

    public void test(String wxid, String msg) {
        Assert.isNull(wxid, "wxid should be provided");
        Assert.isNull(msg, "message should be provided");
        if (StrUtil.equalsIgnoreCase(wxid, wxIdProperties.getYoga())) {
            if (StrUtil.containsIgnoreCase(StrUtil.trim(msg), "帅")) {
                commonService.sendGroup(wxid, getContent());
            }
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
