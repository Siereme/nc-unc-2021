package com.app.controller;

import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.app.ConstantVariables.*;

@SuppressWarnings("SameReturnValue")
@Controller
public class RegistrationController {
    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    @Lazy
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute(USER_FORM, user);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, Model model) {

        String username = userForm.getUsername();
        if (username.length() < 3) {
            model.addAttribute(USERNAME_ERROR, "username must contain at least 3 characters");
            return "registration";
        }
        String password = userForm.getPassword();
        if (password.length() < 6) {
            model.addAttribute(PASSWORD_ERROR, "password must contain at least 6 characters");
            return "registration";
        }

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute(PASSWORD_ERROR, "passwords don't match");
            return "registration";
        } else {
            userService.addRoleToUser(userForm, "ROLE_USER");
            userService.addRoleToUser(userForm, "ROLE_NO_CONFIRMED");
            if (!userService.saveUser(userForm)) {
                model.addAttribute(USERNAME_ERROR, "a user with this name already exists");
            }
            return "login";
        }
    }
}
