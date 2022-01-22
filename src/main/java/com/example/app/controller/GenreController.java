package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class GenreController {

    public String get(ModelMap model){

        return "genres";
    }
}
