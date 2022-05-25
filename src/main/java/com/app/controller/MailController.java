package com.app.controller;

import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.service.mail.MailService;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;

@Controller
public class MailController {

    @Lazy
    private final MailService mailService;

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public MailController(MailService mailService, UserService userService, UserRepository userRepository) {
        this.mailService = mailService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/sendHtmlEmail")
    public String getHtmlEmail() throws MessagingException {
        mailService.sendMessageToConfirmEmail();
        return "redirect:/films/all";
    }

    @GetMapping("/nc-unc-2021/confirm-email/{token}")
    public String verificationToken(@PathVariable("token") String token) throws Exception {
        if (userService.isTokenExist(token)) {
            User currentUser = userService.getCurrentUser();
            userService.removeRoleFromUser(currentUser, "ROLE_NO_CONFIRMED");
            userRepository.save(currentUser);
            mailService.sendSuccessfulConfirmMessage();
        }
        return "redirect:/films/all";
    }

}
