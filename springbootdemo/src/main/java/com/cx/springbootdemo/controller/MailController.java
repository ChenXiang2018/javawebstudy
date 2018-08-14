package com.cx.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    JavaMailSender jms;

    @GetMapping("/send")
    public String send(){
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom("***@qq.com");
        //接收者
        mainMessage.setTo("****@qq.com");
        //发送的标题
        mainMessage.setSubject("hello");
        //发送的内容
        mainMessage.setText("hello world");
        jms.send(mainMessage);
        return "1";
    }
}
