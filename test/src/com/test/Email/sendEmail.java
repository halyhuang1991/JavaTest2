package com.test.Email;

// import java.io.UnsupportedEncodingException;
// import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class sendEmail {
    public static void send() {

        try {
            Properties properties = new Properties();
            // 开启debug调试 ，打印信息
            properties.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            properties.setProperty("mail.smtp.auth", "true");
            // 发送服务器端口，可以不设置，默认是25
            // properties.setProperty("mail.smtp.port", "25");
            // 发送邮件协议名称
            properties.setProperty("mail.transport.protocol", "smtp");
            // 设置邮件服务器主机名
            properties.setProperty("mail.host", "smtp.163.com");
            // 环境信息
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 在session中设置账户信息，Transport发送邮件时会使用
                    return new PasswordAuthentication("h97*02@163.com", "*");
                }
            });

            // 创建邮件对象
            Message message = new MimeMessage(session);
            // 设置主题
            message.setSubject("Tets email");
            // 发件人
            message.setFrom(new InternetAddress("h97*02@163.com"));
            // 多个收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("*@139.com"));
            // // 抄送人
            // message.setRecipient(Message.RecipientType.CC, new
            // InternetAddress("reciveuser1@qq.com"));
            // // 暗送人
            // message.setRecipient(Message.RecipientType.BCC, new
            // InternetAddress("reciveuser2@qq.com"));
            // HTML内容
            message.setContent("<a href='http://blog.csdn.net'>Hello Boy!!</a>", "text/html;charset=utf-8");

            // 连接邮件服务器、发送邮件、关闭连接，全做了
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}