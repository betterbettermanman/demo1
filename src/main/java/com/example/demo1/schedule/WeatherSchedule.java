package com.example.demo1.schedule;

import com.example.demo1.service.CommonService;
import com.example.demo1.service.TaskService;
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
    @Autowired
    private TaskService taskService;

    //3.添加定时任务
    @Scheduled(cron = "0 0 6 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        //菜
        commonService.sendInfo("wxid_uyu8cpztrem522", weatherService.getWeather(510182));
        //猫咪
        commonService.sendInfo("wxid_r6t23z9oht5t21", weatherService.getWeather(511400));

    }

    /**
     * 贷款提醒定时任务
     */
    @Scheduled(cron = "0 0 6 28 * ?")
    private void Task3() {
        //菜
        commonService.sendInfo("wxid_uyu8cpztrem522", "还贷款拉！！");
        //猫咪
        commonService.sendInfo("wxid_r6t23z9oht5t21", "还贷款拉!!");
    }

    /**
     * 添加每周五发送周报提醒
     */
    @Scheduled(cron = "0 0 17 ? * FRI")
    private void Task4() {
        //菜
        commonService.sendInfo("wxid_uyu8cpztrem522", "发周报拉！！");
    }

    /**
     * 每天10点提醒软著情况
     */
    @Scheduled(cron = "0 0 10 * * ?")
    private void Task5() {
        commonService.sendInfo("24598305711@chatroom", "软著 软著 不要忘记了，22号就要交了");
    }


    /**
     * 每天7点提醒工作任务计划
     */
    @Scheduled(cron = "0 0 7 * * ?")
    private void Task6() {
        taskService.remindTask();
    }

    /**
     * 每天21点提醒第二天天气情况
     */
    @Scheduled(cron = "0 0 21 * * ?")
    private void Task7() {
        commonService.sendInfo("wxid_r6t23z9oht5t21", weatherService.getWeather2(511400));
    }


}
