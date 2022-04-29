package com.app.controller;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.repository.ActorsRepository;
import com.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.app.Constants.*;

@SuppressWarnings({"SameReturnValue", "unused"})
@Validated
@Controller
@RequestMapping(path = "/actors")
@SessionAttributes({"errors", "success"})
public class ActorController {
    private static final Logger logger = Logger.getLogger(ActorController.class);

    private void getActorsAndFilmsList(Collection<Actor> actorCollection, ModelMap model) {
        model.addAttribute(ACTORS, actorCollection);
        Collection<Collection<Film>> listListFilms = new LinkedList<>();
        for (Actor actor : actorCollection) {
            listListFilms.add(actor.getFilms());
        }
        model.addAttribute(FILMS, listListFilms);
        model.addAttribute(JSON, "../serialize/actors");
        logger.info("show all actors");
    }

    @Autowired
    private ActorsRepository repository;

    @Autowired
    private FilmsRepository filmsRepository;

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        Collection<Actor> actorList = repository.findAll();
        getActorsAndFilmsList(actorList, model);
        return ACTORS;
    }

    @GetMapping(value = "/errors")
    public String getWithErrors(@ModelAttribute("errors") List<String> errors, ModelMap model, SessionStatus sessionStatus) {
        if(!errors.isEmpty()){
            model.addAttribute(ERRORS, errors);
            sessionStatus.setComplete();
        }
        return get(model);
    }

    @GetMapping(value = "/success")
    public String getWithSuccess(@ModelAttribute("success") List<String> success, ModelMap model, SessionStatus sessionStatus) {
        if(!success.isEmpty()){
            model.addAttribute(SUCCESS, success);
        }
        sessionStatus.setComplete();
        return get(model);
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/actors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        Collection<Actor> actorList = repository.findByContains(tittle);
        getActorsAndFilmsList(actorList, model);
        return new ModelAndView(ACTORS, model);
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Actor actor, ModelMap model,
                                   @PathVariable @NotBlank String commandType) {
        Collection<Film> films = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute(FILMS, films);
            model.addAttribute(MODAL_TITLE, "Add");
            model.addAttribute(EVENT_TYPE, "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            int id = actor.getId();
            actor = repository.findById(id);
            Collection<Film> actorFilmList = actor.getFilms();
            // we delete entities that are both there and there in films
            // ( удаляем из списка всех фильмов те, в которых актер участвовал)
            films.removeIf(film -> actorFilmList.stream().anyMatch(actorFilm -> actorFilm.getId() == film.getId()));
            model.addAttribute(FILMS, films);
            model.addAttribute(FILM_LIST, actorFilmList);
            model.addAttribute(MODAL_TITLE, "Edit");
            model.addAttribute(EVENT_TYPE, "handle/edit");
            model.addAttribute("actor", actor);
        }
        return "actor-handle";
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Actor actor, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(actor, map, "page-add");
        }
        repository.add(actor);
        return "redirect:/actors/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Actor actor, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
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
