package com.example.app.controller;

import com.example.app.model.film.Film;
import com.example.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/films")
public class FilmController {
    private final Logger logger = Logger.getLogger(FilmController.class.getName());


    @GetMapping(value = "/find")
    public String get (ModelMap model){
        List<Film> films = repository.findAll();
        model.addAttribute("title", "Films");
        model.addAttribute("films", films);
        logger.info("Show all films");
        return "films";
    }

    @PostMapping(path="/find")
    public String get (@ModelAttribute Film requestFilm, ModelMap model){
        List<Film> findFilm = new ArrayList<>();
        for(Film film : repository.findAll()){
            if(Objects.equals(film.getTittle(), requestFilm.getTittle())){
                findFilm.add(film);
            }
        }
        if(findFilm.size() > 0){
            model.addAttribute("title", "Films");
            model.addAttribute("films", findFilm);
            return "films";
        }
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
