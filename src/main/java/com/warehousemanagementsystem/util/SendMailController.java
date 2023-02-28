package com.warehousemanagementsystem.util;

import com.warehousemanagementsystem.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {
    @Autowired(required = false)
    private JavaMailSender mailSender;
    //@Value("${配置文件中的某个关键字})得到配置文件文件中的某个值
    @Value("${spring.mail.username}")
    private String sender;
    // @Scheduled(cron = "0 30 8 L * ?")
    @RequestMapping(value = "send_mail")
    public RestResult sendMail(
            String subject,
            String text,
            String to
    ) {
        //     创建一个简单邮件模板对象
        SimpleMailMessage message = new SimpleMailMessage();
        //    设置邮件发送信息
        message.setFrom(sender);//设置发件人
        message.setTo(to);//设置收件人邮箱地址
        message.setSubject(subject);//设置邮件标题
        message.setText(text);//邮件文本
        // 发送
        try {
            mailSender.send(message);
            return RestResult.success(0,"发送成功");
        }catch (MailException e){
            return RestResult.success(1,"发送失败"+e.getMessage());
        }
    }

}
