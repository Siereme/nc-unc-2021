package com.app.controller;

import com.app.model.user.User.User;
import com.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    @Lazy
    @Autowired
    private UserRepository repository;



    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("userForm", user);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, Model model) {

        String username = userForm.getUsername();
        if(username.length() < 3) {
            model.addAttribute("usernameError", "username must contain at least 3 characters");
            return "registration";
        }
        String password = userForm.getPassword();
        if(password.length() < 6){
            model.addAttribute("passwordError", "password must contain at least 6 characters");
            return "registration";
        }

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "passwords don't match");
            return "registration";
        }
        if (!repository.saveUser(userForm)){
            model.addAttribute("usernameError", "a user with this name already exists");
            return "registration";
        }

        return "redirect:/login";
    }





}