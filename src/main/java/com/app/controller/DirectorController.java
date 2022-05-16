package com.app.controller;

import com.app.ConstantVariables;
import com.app.model.actor.Actor;
import com.app.repository.FilmsRepository;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.repository.DirectorsRepository;
import com.app.service.SequenceGeneratorService;
import com.app.service.director.DirectorService;
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

import static com.app.ConstantVariables.*;

@SuppressWarnings("ALL")
@Validated
@Controller
@RequestMapping(path = "/directors")
@SessionAttributes({"errors", "success"})
public class DirectorController {
    private static final Logger logger = Logger.getLogger(DirectorController.class);

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private DirectorsRepository repository;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private FilmsRepository filmsRepository;

    private void getDirectorsAndFilmsList(Collection<Director> directorCollection, ModelMap model) {
        model.addAttribute(DIRECTORS, directorCollection);
        Collection<Collection<Film>> listFilms = new LinkedList<>();
        for (Director director : directorCollection) {
            Collection<Integer> filmsIds = director.getFilmsIds();
            List<Film> filmCollection = (List<Film>) filmsRepository.findAllById(filmsIds);
            listFilms.add(filmCollection);
        }
        model.addAttribute(FILMS, listFilms);
        model.addAttribute(JSON, "../serialize/directors");
        logger.info("show all directors");
    }

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        Collection<Director> directorList = repository.findAll();
        getDirectorsAndFilmsList(directorList, model);
        return DIRECTORS;
    }

    @GetMapping(value = "/errors")
    public String getWithErrors(@ModelAttribute("errors") List<String> errors, ModelMap model,
                                SessionStatus sessionStatus) {
        if (!errors.isEmpty()) {
            model.addAttribute(ERRORS, errors);
            sessionStatus.setComplete();
        }
        return get(model);
    }

    @GetMapping(value = "/success")
    public String getWithSuccess(@ModelAttribute("success") List<String> success, ModelMap model,
                                 SessionStatus sessionStatus) {
        if (!success.isEmpty()) {
            model.addAttribute(SUCCESS, success);
        }
        sessionStatus.setComplete();
        return get(model);
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/directors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        Collection<Director> directorList = directorService.findByContains(tittle);
        getDirectorsAndFilmsList(directorList, model);
        return new ModelAndView(DIRECTORS, model);
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Director director, ModelMap model,
                                   @PathVariable @NotBlank String commandType) {
        List<Film> films = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute(FILMS, films);
            model.addAttribute(MODAL_TITLE, "Add");
            model.addAttribute(EVENT_TYPE, "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            // TODO
/*            int id = director.getId();
            director = repository.findById(id);
            Collection<Integer> filmsByDirectorId = director.getFilmsIds();
            films.removeIf(film -> filmsByDirectorId.stream().anyMatch(directorFilm -> directorFilm.getId() == film.getId()));
            model.addAttribute(FILMS, films);
            model.addAttribute(FILM_LIST, filmsByDirectorId);
            model.addAttribute(MODAL_TITLE, "Edit");
            model.addAttribute(EVENT_TYPE, "handle/edit");
            model.addAttribute("director", director);*/
        }
        return "director-handle";
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Director director, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(director, map, "page-add");
        }
        director.setId(sequenceGeneratorService.generateSequence(Actor.SEQUENCE_NAME));
        repository.save(director);
        return "redirect:/directors/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Director director, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(director, map, "page-edit");
        }
        repository.save(director);
        return "redirect:/directors/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/directors/all");
    }

}
