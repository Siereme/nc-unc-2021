package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;

import java.util.List;

public class Consumer implements Runnable {

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
            while (true) {
                if (emailsRepository.isEmpty()) {
                    break;
                }
                if (cnt == 5) {
                    cnt = 0;
                    Thread.sleep(2000);
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
