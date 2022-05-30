package com.app.service.mail;

import com.app.model.IEntity;
import com.app.model.emailInfo.NewEmail;
import com.app.service.mail.kafka.KafkaProducer;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MailHandler {

    private final KafkaProducer producer;

    @Autowired
    public MailHandler(KafkaProducer producer) {
        this.producer = producer;
    }

    @Pointcut("@annotation(com.app.annotation.AddEntityHandler) && args(entity))")
    private void addEntityHandle(IEntity entity) {
    }

    @AfterReturning("addEntityHandle(entity)")
    public void collectMail(IEntity entity) {
        if (entity != null) {
            String type = entity.getClass().getSimpleName();
            String text = entity.toString();
            NewEmail newEmail = new NewEmail(type, text, "null", "team nc-unc-2021");
            producer.addEmail(newEmail);
        }
    }
}
