package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class Consumer implements Runnable {

    @Autowired
    private MailService mailService;

    @Autowired
    Consumer(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    private List<NewEmail> newEmailList = new LinkedList<>();

    private EmailsRepository emailsRepository;

    public List<NewEmail> getNewEmailList() {
        return newEmailList;
    }

    public void setNewEmailList(List<NewEmail> newEmailList) {
        this.newEmailList = newEmailList;
    }

    public EmailsRepository getEmailsRepository() {
        return emailsRepository;
    }

    public void setEmailsRepository(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public void run() {
        try {
            // это чтобы раньше не начать, хотя это надо будет обыграть по-другому
            Thread.sleep(5000);
            int cnt = 0;
            while (!emailsRepository.isEmpty()) {
                if (cnt == 2) {
                    cnt = 0;
                    // TODO отправлять письма здесь надо наверное
                    for (NewEmail email : newEmailList) {
                        Map<String, Object> context = new HashMap<>();
                        context.put("text", email.getText());
                        context.put("senderName", email.getFrom());
                        String to = email.getTo();
                        mailService.sendMessageUsingThymeleafTemplate(to, "hello, we have an update!!!", context);
                    }
                    newEmailList.clear();
                    Thread.sleep(2000);
                }

                NewEmail newEmail = emailsRepository.getNewFilmEmails().take();
                newEmailList.add(newEmail);

                ++cnt;
            }
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
