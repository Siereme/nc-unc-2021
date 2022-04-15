package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer implements Runnable {

    @Autowired
    Consumer(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    private List<NewEmail> newEmailList;

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
            int cnt = 0;
            while (!emailsRepository.isEmpty()) {

                if (cnt == 5) {
                    cnt = 0;
                    Thread.sleep(2000);
                    // TODO отправлять письма здесь надо наверное
                }

                NewEmail newEmail = emailsRepository.getNewFilmEmails().take();
                newEmailList.add(newEmail);

                ++cnt;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
