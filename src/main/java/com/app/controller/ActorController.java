package com.app.controller;

import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import com.app.repository.FilmsRepository;
import com.app.model.film.Film;
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
import java.util.Set;

@Validated
@Controller
@RequestMapping(path = "/actors")
public class ActorController {
    private final Logger logger = Logger.getLogger(ActorController.class.getName());

    @Autowired
    private ActorsRepository repository;

    @Autowired
    private FilmsRepository filmsRepository;

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        List<Actor> actorList = repository.findAll();
        model.addAttribute("actors", actorList);
        List<List<Film>> listListFilms = new LinkedList<>();
        for (Actor actor : actorList) {
            Set<Film> filmSet = actor.getFilms();
            List<Film> filmList = new LinkedList<>(filmSet);
            listListFilms.add(filmList);
        }
        model.addAttribute("films", listListFilms);
        model.addAttribute("json", "../serialize/actors");
        logger.info("show all actors");
        return "actors";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/actors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        List<Actor> actorList = repository.findByContains(tittle);
        if (actorList == null || actorList.isEmpty()) {
            return new ModelAndView("redirect:/actors/all");
        } else {
            model.addAttribute("actors", actorList);
            List<List<Film>> listListFilms = new LinkedList<>();
            for (Actor actor1 : actorList) {
                Set<Film> filmSet = actor1.getFilms();
                List<Film> filmList1 = new LinkedList<>(filmSet);
                listListFilms.add(filmList1);
            }
            model.addAttribute("films", listListFilms);
            model.addAttribute("json", "../serialize/actors");
            logger.info("show all actors");
            return new ModelAndView("actors", model);
        }
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Actor actor, ModelMap model,
                                   @PathVariable @NotBlank String commandType) {
        List<Film> films = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute("filmList", films);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            List<Film> actorFilmList = repository.findFilmsByActorId(actor.getId());
            // we delete entities that are both there and there in films
            // ( удаляем из списка всех фильмов те, в которых актер участвовал)
            films.removeIf(film -> actorFilmList.stream().anyMatch(actorFilm -> actorFilm.getId() == film.getId()));
            model.addAttribute("filmList", films);
            model.addAttribute("actorFilmList", actorFilmList);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "handle/edit");
            model.addAttribute("actor", actor);
        }
        return "actor-handle";
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Actor actor, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(actor, map, "page-add");
        }
        repository.add(actor);
        return "redirect:/actors/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Actor actor, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(actor, map, "page-edit");
        }
        repository.edit(actor);
        return "redirect:/actors/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/actors/all");
    }

}
