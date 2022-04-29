package com.app.service.mail;

import com.app.model.emailInfo.NewEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddingHandler {

    private final Producer producer;

    @Autowired
    public AddingHandler(Producer producer) {
        this.producer = producer;
        Thread sender = new Thread(producer);
//        Thread getter = new Thread(consumer);
        sender.start();
//        getter.start();
    }

    public void addEmailsListToProducer(List<NewEmail> emails) {
        producer.addNewEmailList(emails);
    }

}
