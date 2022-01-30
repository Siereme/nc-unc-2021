package com.example.app.controller;

import com.example.app.model.user.User.User;
import com.example.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final Logger logger = Logger.getLogger(RegistrationController.class.getName());

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

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "passwords don't match");
            return "registration";
        }
        if (!repository.saveUser(userForm)){
            model.addAttribute("usernameError", "a user with this name already exists");
            return "registration";
        }

        return "redirect:/";
    }





}
