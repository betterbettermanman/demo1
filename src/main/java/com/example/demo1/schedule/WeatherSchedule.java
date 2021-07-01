package com.example.demo1.schedule;

import com.example.demo1.service.CommonService;
import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    //贷款提醒定时任务
    @Scheduled(cron = "0 0 6 28 * ?")
    private void Task3() {
        commonService.sendInfo("wxid_uyu8cpztrem522", "还贷款拉！！");//菜
        commonService.sendInfo("wxid_r6t23z9oht5t21", "还贷款拉!!");//猫咪
    }
}
