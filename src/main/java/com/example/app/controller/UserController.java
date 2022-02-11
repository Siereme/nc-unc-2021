package com.example.app.controller;

import com.example.app.model.user.IUser;
import com.example.app.model.user.User.User;
import com.example.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserRepository repository = new UserRepository();

}
