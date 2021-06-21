package com.example.demo1.schedule;

import com.example.demo1.service.CommonService;
import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class WeatherSchedule {
    @Autowired
    private CommonService commonService;
    @Autowired
    private WeatherService weatherService;

    //3.添加定时任务
    @Scheduled(cron = "0 0 6 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        commonService.sendInfo("wxid_uyu8cpztrem522", weatherService.getWeather(510182));//菜
        commonService.sendInfo("wxid_r6t23z9oht5t21", weatherService.getWeather(511400));//猫咪

    }
    private static List list = new ArrayList<>();
    static{
        list.add("张孬糟");
        list.add("摊摊面");
        list.add("牛肉粉");
        list.add("食堂");
        list.add("如意小面");
        list.add("兰州拉面");
        list.add("厨神炒饭");
    }
    //干饭群定时任务
    @Scheduled(cron="0 40 11 * * ?")
    private void Task2(){
        commonService.sendGroup("20112924608@chatroom", "恰饭了，恰饭了，中午吃啥子！");
        for(int i=0;i<list.size();i++){
            commonService.sendGroup("20112924608@chatroom", (i+1)+"."+list.get(i));
        }
    }
    //贷款提醒定时任务
    @Scheduled(cron="0 0 6 22 * ?")
    private void Task3(){
        commonService.sendInfo("wxid_uyu8cpztrem522", "测试消息：还贷款拉！！");//菜
        commonService.sendInfo("wxid_r6t23z9oht5t21", "测试消息：还贷款拉!!");//猫咪
    }
}
