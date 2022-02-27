package com.app.controller;

import com.app.repository.FilmsRepository;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.repository.DirectorsRepository;
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

@Validated
@Controller
@RequestMapping(path = "/directors")
@SessionAttributes({"errors", "success"})
public class DirectorController {
    private static final Logger logger = Logger.getLogger(DirectorController.class);

    @Autowired
    private final DirectorsRepository repository = new DirectorsRepository();

    @Autowired
    private final FilmsRepository filmsRepository = new FilmsRepository();

    private void getDirectorsAndFilmsList(Collection<Director> directorCollection, ModelMap model) {
        model.addAttribute("directors", directorCollection);
        Collection<Collection<Film>> listListFilms = new LinkedList<>();
        for (Director director : directorCollection) {
            listListFilms.add(director.getFilms());
        }
        model.addAttribute("films", listListFilms);
        model.addAttribute("json", "../serialize/directors");
        logger.info("show all directors");
    }

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        Collection<Director> directorList = repository.findAll();
        getDirectorsAndFilmsList(directorList, model);
        return "directors";
    }

    @GetMapping(value = "/errors")
    public String getWithErrors(@ModelAttribute("errors") List<String> errors, ModelMap model, SessionStatus sessionStatus) {
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            sessionStatus.setComplete();
        }
        return get(model);
    }

    @GetMapping(value = "/success")
    public String getWithSuccess(@ModelAttribute("success") List<String> success, ModelMap model, SessionStatus sessionStatus) {
        if(!success.isEmpty()){
            model.addAttribute("success", success);
        }
        sessionStatus.setComplete();
        return get(model);
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/directors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        Collection<Director> directorList = repository.findByContains(tittle);
        if (directorList == null || directorList.isEmpty()) {
            return new ModelAndView("redirect:/directors/all");
        } else {
            getDirectorsAndFilmsList(directorList, model);
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
            int id = director.getId();
            director = repository.findById(id);
            Collection<Film> filmsByDirectorId = director.getFilms();
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
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(director, map, "page-add");
        }
        repository.add(director);
        return "redirect:/directors/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Director director, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(director, map, "page-edit");
        }
        repository.edit(director);
        return "redirect:/directors/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/directors/all");
    }

}
