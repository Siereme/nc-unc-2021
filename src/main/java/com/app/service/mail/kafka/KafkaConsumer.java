package com.app.service.mail.kafka;

import com.app.model.emailInfo.NewEmail;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
public class KafkaConsumer {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

//    private static final Logger logger = Logger.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "email", groupId = "group-id")
    public void consume(NewEmail email) throws IOException, MessagingException {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            String to = user.getEmail();
            String subject = "Added new " + email.getType().toLowerCase(Locale.ROOT) + "!";
            mailService.sendNewUpdatedMessage(to, subject, email);
        }
//        logger.info(String.format("#### -> Consumed message -> %s", email));
    }

}
