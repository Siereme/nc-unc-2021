package com.example.app.controller;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.repository.DirectorsRepository;
import com.example.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Validated
@Controller
@RequestMapping(path = "/directors")
public class DirectorController {
    private final Logger logger = Logger.getLogger(DirectorController.class.getName());

    @Autowired
    private DirectorsRepository repository = new DirectorsRepository();

    @Autowired
    private FilmsRepository filmsRepository = new FilmsRepository();

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        List<Director> directorList = repository.findAll();
        model.addAttribute("directors", directorList);
        List<List<Film>> listListFilms = new LinkedList<>();
        for (Director director : directorList) {
            Integer id = director.getId();
            List<Film> films = repository.findFilmsByDirectorId(id);
            listListFilms.add(films);
        }
        model.addAttribute("films", listListFilms);
        model.addAttribute("json", "../serialize/directors");
        logger.info("show all directors");
        return "directors";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/directors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        List<Director> directorList = repository.findByContains(tittle);
        if (directorList == null || directorList.isEmpty()) {
            return new ModelAndView("redirect:/directors/all");
        } else {
            model.addAttribute("directors", directorList);
            List<List<Film>> listListFilms = new LinkedList<>();
            for (Director director : directorList) {
                Integer id = director.getId();
                List<Film> films = repository.findFilmsByDirectorId(id);
                listListFilms.add(films);
            }
            model.addAttribute("films", listListFilms);
            model.addAttribute("json", "../serialize/directors");
            logger.info("show all directors");
            return new ModelAndView("directors", model);
        }
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Director director, ModelMap model,
                                   @PathVariable @NotBlank String commandType) {
        List<Film> films = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute("filmList", films);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            List<Film> filmsByDirectorId = repository.findFilmsByDirectorId(director.getId());
            films.removeIf(film -> filmsByDirectorId.stream().anyMatch(actorFilm -> actorFilm.getId() == film.getId()));
            model.addAttribute("filmList", films);
            model.addAttribute("directorFilmList", filmsByDirectorId);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "handle/edit");
            model.addAttribute("director", director);
        }
        return "director-handle";
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Director director, BindingResult result, ModelMap map) {
        if(result.hasErrors()){
            map.addAttribute("result", result);
            return renderHandlePage(director, map, "page-add");
        }
        repository.add(director);
        return "redirect:/directors/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Director director, BindingResult result, ModelMap map) {
        if(result.hasErrors()){
            map.addAttribute("result", result);
            return renderHandlePage(director, map, "page-edit");
        }
        repository.edit(director);
        return "redirect:/directors/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/actors/all");
    }

}
