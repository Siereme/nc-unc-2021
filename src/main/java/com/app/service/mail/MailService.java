package com.app.service.mail;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.emailInfo.NewEmail;
import com.app.model.user.User;
import com.app.repository.ConfirmEmailRepository;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MailService {

    @Autowired
    protected JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
    @Autowired
    UserService userService;
    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;

    private String to;
    private final String subject = "[nc-unc-2021] Please confirm your email address!";
    private final String from = "team nc-unc-2021";

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("email.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    public void sendNewUpdatedMessage(String to, String subject, NewEmail email) throws MessagingException {
        Context context = new Context();
        context.setVariable("type", email.getType());
        context.setVariable("text", email.getText());
        context.setVariable("from", email.getFrom());
        String html = thymeleafTemplateEngine.process("email.html", context);

        sendHtmlMessage(to, subject, html);
    }

    public String getMessageToConfirmEmail() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi ");
        String currentUserName = userService.getCurrentUsername();
        sb.append(currentUserName);
        sb.append(",\n");
        sb.append("Thanks for using our service. Please verify your email address by clicking the button below.");
        return new String(sb);
    }

    public String getMessageSuccessfulConfirm() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi ");
        String currentUserName = userService.getCurrentUsername();
        sb.append(currentUserName);
        sb.append(",\n");
        sb.append("Thanks for using our service. Your email successfully confirmed!");
        return new String(sb);
    }

    public void addTokenToDB(String token) {
        User user = userService.getCurrentUser();
        ConfirmEmail confirmEmail = new ConfirmEmail(user.getUser_id(), token);
        confirmEmailRepository.add(confirmEmail);
    }

    public String getConfirmLink() {
        UUID uuid = java.util.UUID.randomUUID();
        String stringUUID = uuid.toString();
        addTokenToDB(stringUUID);
        StringBuffer sb = new StringBuffer();
        sb.append("http://localhost:8080/").append("nc-unc-2021/").append("confirm-email/").append(stringUUID);
        return new String(sb);
    }

    public void sendMessageToConfirmEmail() throws MessagingException {
        User user = userService.getCurrentUser();
        if (!userService.isLinksEnough(user, 1)) {
            Map<String, Object> context = new HashMap<>();
            //        context.put("name", "Name");
            String text = getMessageToConfirmEmail();
            context.put("text", text);
            context.put("from", from);
            String link = getConfirmLink();
            context.put("link", link);
            to = userService.getCurrentEmail();
            sendMessageUsingThymeleafTemplate(to, subject, context);
        }
    }

    public void sendSuccessfulConfirmMessage() throws MessagingException {
        Map<String, Object> context = new HashMap<>();
        //        context.put("name", "Name");
        String text = getMessageSuccessfulConfirm();
        context.put("text", text);
        context.put("from", from);
        String link = getConfirmLink();
        context.put("link", link);
        to = userService.getCurrentEmail();
        sendMessageUsingThymeleafTemplate(to, subject, context);
    }
}
