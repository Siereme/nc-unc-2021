package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Producer implements Runnable {

    private EmailsRepository emailsRepository;

    private List<NewEmail> newEmailList;

    public EmailsRepository getEmailsRepository() {
        return emailsRepository;
    }

    public void setEmailsRepository(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    public List<NewEmail> getNewEmailList() {
        return newEmailList;
    }

    public void setNewEmailList(List<NewEmail> newEmailList) {
        this.newEmailList = newEmailList;
    }

    @Autowired
    public Producer(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public void run() {
        try {
            int cnt = 0;
            for (int i = 0; i < newEmailList.size(); ++i) {
                NewEmail newEmail = newEmailList.remove(0);
                emailsRepository.getNewFilmEmails().add(newEmail);
                ++cnt;
                if (cnt++ > 2) {
/*                    NewEmail done = new NewEmail("done", " ");
                    emailsRepository.getNewFilmEmails().put(done);*/
                    cnt = 0;
                    Thread.sleep(10000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
