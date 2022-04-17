package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddingHandler {

    private final Producer producer;

    @Autowired
    public AddingHandler(Producer producer, Consumer consumer) {
        this.producer = producer;
        Thread sender = new Thread(producer);
        Thread getter = new Thread(consumer);
        sender.start();
        getter.start();
    }

    public void setEmailsListToProducer(List<NewEmail> emails) {
        producer.setNewEmailList(emails);
    }

}
