package com.app.controller;

import com.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserRepository repository = new UserRepository();

}
