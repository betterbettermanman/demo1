package com.example.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    public String sendMail(String title,String text,String targetMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject(title);
        message.setText(text);
        message.setTo(targetMail);
        message.setFrom(username);
        javaMailSender.send(message);
        return "简单邮件发送成功！";
    }
}
