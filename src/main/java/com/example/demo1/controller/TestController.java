package com.example.demo1.controller;

import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/test")
    public void test() {
        weatherService.getWeather(511400);
    }
}
