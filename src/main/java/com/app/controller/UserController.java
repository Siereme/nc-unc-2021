package com.app.controller;

import com.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

}
