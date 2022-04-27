package com.app.service.mail.job;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class QueueItemWriter implements ItemWriter<NewEmail> {

    @Autowired
    protected JavaMailSender emailSender;

    @Autowired
    UserRepository repository;

    @Override
    public void write(List<? extends NewEmail> list) throws Exception {
        list.forEach(email -> {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            try {
                helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo("sergeyskotenev@gmail.com");
                helper.setSubject("Added new content!");
                helper.setText(email.getText(), true);
                emailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
