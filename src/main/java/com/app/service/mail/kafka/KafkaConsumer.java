package com.app.service.mail.kafka;

import com.app.model.emailInfo.NewEmail;
import com.app.service.mail.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Locale;

@Service
public class KafkaConsumer {

    @Autowired
    private MailService mailService;

    private static final Logger logger = Logger.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "email", groupId = "group-id")
    public void consume(NewEmail email) throws IOException, MessagingException {
        String to = email.getTo();
        mailService.sendNewUpdatedMessage(to, "Added new " + email.getType().toLowerCase(Locale.ROOT) + "!", email);
        logger.info(String.format("#### -> Consumed message -> %s", email));
    }
}
