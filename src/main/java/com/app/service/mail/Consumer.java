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

    @Lazy
    private final MailService mailService;

    @Autowired
    Consumer(EmailsRepository emailsRepository, MailService mailService) {
        this.emailsRepository = emailsRepository;
        this.mailService = mailService;
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
/*        try {
            // это чтобы раньше не начать, хотя это надо будет обыграть по-другому
            System.out.println("consumer run");
            Thread.sleep(5000);
            System.out.println("consumer get up");
            int cnt = 0;
            while (!emailsRepository.isEmpty() || !(newEmailList.isEmpty())) {
                if (cnt == 2) {
                    cnt = 0;
                    for (NewEmail email : newEmailList) {
                        Map<String, Object> context = new HashMap<>();
                        context.put("text", email.getText());
                        context.put("senderName", email.getFrom());
                        String to = email.getTo();
                        System.out.println("consumer sends email");
                        mailService.sendMessageUsingThymeleafTemplate(to, "hello, we have an update!!!", context);
                    }
                    newEmailList.clear();
                    Thread.sleep(20000);
                }
                NewEmail newEmail = emailsRepository.getNewFilmEmails().take();
                System.out.println("consumer get email to");
                System.out.println(newEmail.getTo());
                newEmailList.add(newEmail);
                System.out.println("consumer add email to email list");
                ++cnt;
            }
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        }*/
        try {
            System.out.println("consumer run");
            int cnt = 0;
            int capacity = emailsRepository.getCapacity();
            while (true) {
                if (cnt == capacity) {
                    cnt = 0;
                    for (NewEmail email : newEmailList) {
                        Map<String, Object> context = new HashMap<>();
                        context.put("text", email.getText());
                        context.put("senderName", email.getFrom());
                        String to = email.getTo();
                        System.out.println("consumer sends email");
                        mailService.sendMessageUsingThymeleafTemplate(to, "hello, we have an update!!!", context);
                    }
                    newEmailList.clear();
                }
                if (!emailsRepository.isEmpty()) {
                    NewEmail newEmail = emailsRepository.take();
                    newEmailList.add(newEmail);
                    ++cnt;
                }
            }
        } catch (MessagingException | InterruptedException e) {
            System.out.println("consumer died");
            e.printStackTrace();
        }

    }
}
