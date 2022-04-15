package com.app.service.mail;

import com.app.model.IEntity;
import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Aspect
@Component
public class MailHandler {

    @Autowired
    EmailsRepository repository;

    @Pointcut("@annotation(com.app.annotation.AddEntityHandler) && args(entity))")
    private void addEntityHandle(IEntity entity){}


    @AfterReturning("addEntityHandle(entity)")
    public void collectMail(IEntity entity){
        if(entity != null){
            String type = entity.getClass().getSimpleName();
            String text = entity.toString();
            NewEmail mail = new NewEmail(type, text);
            Producer producer = new Producer(repository);
            producer.setNewEmailList(Collections.singletonList(mail));
            Thread sender = new Thread(producer);
            sender.start();
        }
    }
}
