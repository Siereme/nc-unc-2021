package com.app.service.mail;

import com.app.model.IEntity;
import com.app.model.emailInfo.NewEmail;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Aspect
@Component
public class MailHandler {

    private final AddingHandler addingHandler;

    private final UserRepository userRepository;

    @Autowired
    public MailHandler(UserRepository userRepository, AddingHandler addingHandler) {
        this.userRepository = userRepository;
        this.addingHandler = addingHandler;
    }

    @Pointcut("@annotation(com.app.annotation.AddEntityHandler) && args(entity))")
    private void addEntityHandle(IEntity entity) {
    }

    @AfterReturning("addEntityHandle(entity)")
    public void collectMail(IEntity entity) {
        if (entity != null) {
            String type = entity.getClass().getSimpleName();
            String text = entity.toString();
            List<User> userList = userRepository.findAll();
            List<NewEmail> emails = new LinkedList<>();
            for (User user : userList) {
                NewEmail newEmail = new NewEmail(type, text, user.getEmail(), "team nc-unc-2021");
                emails.add(newEmail);
            }
            addingHandler.addEmailsListToProducer(emails);
        }
    }
}
