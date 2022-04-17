package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class Producer implements Runnable {

    private EmailsRepository emailsRepository;

    private List<NewEmail> newEmailList = new LinkedList<>();

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
/*        try {
            System.out.println("producer run");
            int cnt = 0;
            for (int i = 0; i < newEmailList.size(); ++i) {
                NewEmail newEmail = newEmailList.remove(0);
                System.out.println("producer get email");
                emailsRepository.getNewFilmEmails().add(newEmail);
                System.out.println("producer added email to repository");
                ++cnt;
                if (cnt == 2) {
                    cnt = 0;
                    System.out.println("producer sleep");
                    Thread.sleep(10000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            System.out.println("producer run");
            int cnt = 0;
            int capacity = emailsRepository.getCapacity();
            while (true) {
                if (cnt == capacity) {
                    cnt = 0;
                    System.out.println("producer sleep");
                    // ставлю 100 секунд, чтобы показать, что письма отправляются пачками
                    Thread.sleep(100000);
                }
                if (!newEmailList.isEmpty()) {
                    NewEmail newEmail = newEmailList.remove(0);
                    System.out.println("producer get email");
                    emailsRepository.add(newEmail);
                    System.out.println("producer added email to repository");
                    ++cnt;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("producer died");
            e.printStackTrace();
        }
    }
}
