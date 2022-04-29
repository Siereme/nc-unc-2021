package com.app.controller;

import com.app.repository.FilmsRepository;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.GenresRepository;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.app.Constants.*;

@SuppressWarnings("ALL")
@Validated
@Controller
@RequestMapping(path = "/genres")
@SessionAttributes({"errors", "success"})
public class GenreController {
    private static final Logger logger = Logger.getLogger(FilmController.class);

    @SuppressWarnings("SameReturnValue")
    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        Collection<Genre> genres = repository.findAll();
        getGenresAndFilms(model, genres);
        logger.info("Show all films");
        return GENRES;
    }

    @GetMapping(value = "/errors")
    public String getWithErrors(@ModelAttribute("errors") @NotEmpty List<String> errors, ModelMap model,
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

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
        Collection<Genre> genres = repository.findByContains(tittle);
        getGenresAndFilms(model, genres);
        return new ModelAndView(GENRES, model);
    }

    private void getGenresAndFilms(ModelMap model, Collection<Genre> genres) {
        Collection<Collection<Film>> films = new LinkedList<>();
        for (Genre genre : genres) {
            films.add(genre.getFilms());
        }
        model.addAttribute(GENRES, genres);
        model.addAttribute(FILMS, films);
        model.addAttribute(JSON, "../serialize/genres");
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Genre genre, ModelMap model,
                                   @Valid @PathVariable String commandType) {
        Collection<Film> filmList = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute(FILMS, filmList);
            model.addAttribute(MODAL_TITLE, "Add");
            model.addAttribute(EVENT_TYPE, "add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            int id = genre.getId();
            genre = repository.findById(id);
            Collection<Film> filmGenreList = genre.getFilms();

            filmList.removeIf(film -> filmGenreList.stream().anyMatch(filmGenre -> filmGenre.getId() == film.getId()));

            model.addAttribute(FILM_LIST, filmGenreList);
            model.addAttribute(FILMS, filmList);
            model.addAttribute(MODAL_TITLE, "Edit");
            model.addAttribute(EVENT_TYPE, "edit");
            model.addAttribute("genre", genre);
        }
        return "genre-handle";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@Valid @PathVariable int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/genres/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Genre genre, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(genre, map, "page-add");
        }
        repository.add(genre);
        return "redirect:/genres/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Genre genre, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(genre, map, "page-edit");
        }
        repository.edit(genre);
        return "redirect:/genres/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/genres/all");
    }

    @Autowired
    private GenresRepository repository;
    @Autowired
    private FilmsRepository filmsRepository;
}
