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
                emailsRepository.getNewFilmEmails().put(newEmailList.remove(0));
                if (cnt++ > 5) {
/*                    NewEmail done = new NewEmail("done", " ");
                    emailsRepository.getNewFilmEmails().put(done);*/
                    cnt = 0;
                    Thread.sleep(2000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
