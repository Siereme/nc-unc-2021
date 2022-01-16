package com.example.app.controller;

import com.example.app.model.film.Film;
import com.example.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/films")
public class FilmController {
    private final Logger logger = Logger.getLogger(FilmController.class.getName());


    @GetMapping(value = "/all")
    public String get (ModelMap model){
        List<Film> films = repository.findAll();
        model.addAttribute("title", "Films");
        model.addAttribute("films", films);
        logger.info("Show all films");
        return "films";
    }

    @PostMapping(value="/find")
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

    @PostMapping(value = "/film-handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Film film, ModelMap model, @PathVariable String commandType){
        if(Objects.equals(commandType, "add")){
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "add");
        }
        if(Objects.equals(commandType, "edit")){
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "edit");
            model.addAttribute("film", film);
        }
        return "film-handle";
    }

    @PostMapping(value = "/delete")
    public RedirectView delete(@ModelAttribute Film film){
        repository.delete(film.getId());
        return new RedirectView("all");
    }

    @PostMapping(value = "/add")
    public RedirectView add(@ModelAttribute Film film){
        repository.add(film);
        return new RedirectView("all");
    }

    @PostMapping(value = "/edit")
    public RedirectView edit(@ModelAttribute Film film){
        repository.edit(film);
        return new RedirectView("all");
    }

    @Autowired
    private FilmsRepository repository = new FilmsRepository();
}
