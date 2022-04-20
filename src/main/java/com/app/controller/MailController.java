package com.app.controller;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.user.User;
import com.app.repository.ConfirmEmailRepository;
import com.app.repository.UserRepository;
import com.app.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MailController {

    @Lazy
    private final MailService mailService;

    private final UserRepository userRepository;

    private final ConfirmEmailRepository confirmEmailRepository;

    @Autowired
    public MailController(MailService mailService, UserRepository userRepository,
                          ConfirmEmailRepository confirmEmailRepository) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.confirmEmailRepository = confirmEmailRepository;
    }

    private String to;
    private final String subject = "[nc-unc-2021] Please confirm your email address!";
    private final String from = "team nc-unc-2021";

    @GetMapping("/sendEmail")
    public String getEmail() {
        mailService.sendSimpleMessage(to, subject, ":=)");
        return "";
    }

    private String getMessageToConfirmEmail() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi ");
        String currentUserName = userRepository.getCurrentUsername();
        sb.append(currentUserName);
        sb.append(",\n");
        sb.append("Thanks for using our service. Please verify your email address by clicking the button below.");
        return new String(sb);
    }

    private String getMessageSuccessfulConfirm() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi ");
        String currentUserName = userRepository.getCurrentUsername();
        sb.append(currentUserName);
        sb.append(",\n");
        sb.append("Thanks for using our service. Your email successfully confirmed!");
        return new String(sb);
    }

    private void addTokenToDB(String token) {
        User user = userRepository.getCurrentUser();
        ConfirmEmail confirmEmail = new ConfirmEmail(user, token);
        confirmEmailRepository.add(confirmEmail);
    }

    private String getConfirmLink() {
        UUID uuid = java.util.UUID.randomUUID();
        String stringUUID = uuid.toString();
        addTokenToDB(stringUUID);
        StringBuffer sb = new StringBuffer();
        sb.append("http://localhost:8080/").append("nc-unc-2021/").append("confirm-email/").append(stringUUID);
        return new String(sb);
    }

    private void sendConfimMessage() throws MessagingException {
        Map<String, Object> context = new HashMap<>();
        //        context.put("name", "Name");
        String text = getMessageToConfirmEmail();
        context.put("text", text);
        context.put("from", from);
        String link = getConfirmLink();
        context.put("link", link);
        to = userRepository.getCurrentEmail();
        mailService.sendMessageUsingThymeleafTemplate(to, subject, context);
    }

    private void sendSuccessfulConfimMessage() throws MessagingException {
        Map<String, Object> context = new HashMap<>();
        //        context.put("name", "Name");
        String text = getMessageSuccessfulConfirm();
        context.put("text", text);
        context.put("from", from);
        String link = getConfirmLink();
        context.put("link", link);
        to = userRepository.getCurrentEmail();
        mailService.sendMessageUsingThymeleafTemplate(to, subject, context);
    }

    @GetMapping("/sendHtmlEmail")
    public String getHtmlEmail() throws MessagingException {
        sendConfimMessage();
        return "redirect:/films/all";
    }

    @GetMapping("/nc-unc-2021/confirm-email/{token}")
    public String verificationToken(@PathVariable("token") String token) throws MessagingException {
        if (userRepository.isTokenExist(token)) {
            User currentUser = userRepository.getCurrentUser();
            userRepository.removeRoleNoConfirmedFromUser(currentUser);
            sendSuccessfulConfimMessage();
        }
        // fixme редирект не совсем корректно работает
        return "redirect:/films/all";
    }


}
