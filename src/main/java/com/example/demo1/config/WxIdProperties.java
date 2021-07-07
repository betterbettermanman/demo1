package com.example.demo1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("wxid")
public class WxIdProperties {
    private String yoga;

    public WxIdProperties() {
    }

    public String getYoga() {
        return yoga;
    }

    public void setYoga(String yoga) {
        this.yoga = yoga;
    }
}




