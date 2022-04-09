package com.app.controller;

import com.app.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MailController {
    @Lazy
    @Autowired
    private MailService mailService;

    private final String to = "sergeyskotenev@gmail.com";
    private final String subject = "hi!";


    @GetMapping("/sendEmail")
    public void getEmail(){
        mailService.sendSimpleMessage(to, subject, ":=)");
    }

    @GetMapping("/sendHtmlEmail")
    public void getHtmlEmail() throws MessagingException {
        Map<String, Object> context = new HashMap<>();
        context.put("name", "Name");
        context.put("text", "Hello");
        context.put("senderName", "SenderName");
        mailService.sendMessageUsingThymeleafTemplate(to, subject, context);
    }
}
