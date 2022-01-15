package com.example.app.controller;

import com.example.app.model.film.Film;
import com.example.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;



@Controller
public class FilmController {
    private final Logger logger = Logger.getLogger(FilmController.class.getName());


    @GetMapping("/films")
    public String get (ModelMap model){
        model.addAttribute("message", "Films");
        return "films";
    }

    @GetMapping(path="/{title}")
    @ResponseBody
    public Film get (@PathVariable String title){
        return null;
    }

    @PostMapping(value = "/post",
            consumes = "application/json",
            produces = "application/json")
    public void add(@RequestBody Film film){
        repository.add(film);
    }

    @DeleteMapping(value = "/delete/{filmId}")
    public void delete(@PathVariable String filmId){
        repository.delete(filmId);
    }

    @Autowired
    private FilmsRepository repository = new FilmsRepository();
}
