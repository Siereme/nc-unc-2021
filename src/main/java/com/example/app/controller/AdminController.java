package com.example.app.controller;

import com.example.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private final Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private UserRepository repository;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", repository.findAll());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) int userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            repository.delete(userId);
        }
        return "redirect:/admin";
    }



}
