package com.app.controller;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.genre.Genre;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.app.model.film.Film;
import com.app.repository.ActorsRepository;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.app.ConstantVariables.*;

@SuppressWarnings({"SameReturnValue", "unused"})
@Validated
@Controller
@RequestMapping(path = "/films")
@SessionAttributes({"errors", "success"})
public class FilmController implements WebMvcConfigurer {
    private static final Logger logger = Logger.getLogger(FilmController.class);

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        Collection<Film> films = repository.findAll();
        Collection<Collection<Genre>> genres = new LinkedList<>();
        Collection<Collection<Director>> directors = new LinkedList<>();
        Collection<Collection<Actor>> actors = new LinkedList<>();
        for (Film film : films) {
            genres.add(film.getGenres());
            directors.add(film.getDirectors());
            actors.add(film.getActors());
        }
        model.addAttribute(FILMS, films);
        model.addAttribute(GENRES, genres);
        model.addAttribute(ACTORS, actors);
        model.addAttribute(DIRECTORS, directors);
        model.addAttribute(JSON, "../serialize/films");
        logger.info("Show all films");

        return "films";
    }

    @GetMapping(value = "/errors")
    public String getWithErrors(@ModelAttribute("errors") List<String> errors, ModelMap model,
                                SessionStatus sessionStatus) {
        if (!errors.isEmpty()) {
            model.addAttribute(ERRORS, errors);
        }
        sessionStatus.setComplete();
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
    public ModelAndView get(@RequestParam("tittle") @NotBlank String tittle, ModelMap model)
            throws ConstraintViolationException {
        Collection<Film> findFilm = repository.findByContains(tittle);
        if (findFilm.size() > 0) {
            Collection<Collection<Genre>> genres = new LinkedList<>();
            Collection<Collection<Actor>> actors = new LinkedList<>();
            Collection<Collection<Director>> directors = new LinkedList<>();
            for (Film film : findFilm) {
                genres.add(film.getGenres());
                actors.add(film.getActors());
                directors.add(film.getDirectors());
            }
            model.addAttribute(FILMS, findFilm);
            model.addAttribute(GENRES, genres);
            model.addAttribute(ACTORS, actors);
            model.addAttribute(DIRECTORS, directors);

            model.addAttribute(JSON, "../serialize/films");
            return new ModelAndView(FILMS, model);
        }
        model.addAttribute(FILMS, findFilm);
        return new ModelAndView("films", model);
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Film film, ModelMap model,
                                   @PathVariable("commandType") @NotBlank String commandType) {
        Collection<Genre> genres = genresRepository.findAll();
        Collection<Actor> actors = actorsRepository.findAll();
        Collection<Director> directors = directorsRepository.findAll();

        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute(GENRES, genres);
            model.addAttribute(ACTORS, actors);
            model.addAttribute(DIRECTORS, directors);
            model.addAttribute(MODAL_TITLE, "Add");
            model.addAttribute(EVENT_TYPE, "add");
        }
        if (Objects.equals(commandType, "page-edit")) {

            int id = film.getId();
            film = repository.findById(id);
            Collection<Genre> genreList = film.getGenres();
            Collection<Actor> actorList = film.getActors();
            Collection<Director> directorList = film.getDirectors();

            genres.removeIf(genre -> genreList.stream().anyMatch(filmGenre -> filmGenre.getId() == genre.getId()));
            actors.removeIf(actor -> actorList.stream().anyMatch(filmActor -> filmActor.getId() == actor.getId()));
            directors.removeIf(director -> directorList.stream()
                    .anyMatch(filmDirector -> filmDirector.getId() == director.getId()));

            model.addAttribute(GENRE_LIST, genreList);
            model.addAttribute(ACTOR_LIST, actorList);
            model.addAttribute(DIRECTOR_LIST, directorList);
            model.addAttribute(GENRES, genres);
            model.addAttribute(ACTORS, actors);
            model.addAttribute(DIRECTORS, directors);
            model.addAttribute(MODAL_TITLE, "Edit");
            model.addAttribute(EVENT_TYPE, "edit");
            model.addAttribute("film", film);
        }
        return "film-handle";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable("id") @NotNull int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/films/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(film, map, "page-add");
        }
        repository.add(film);
        return "redirect:/films/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(film, map, "page-edit");
        }
        repository.edit(film);
        return "redirect:/films/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/films/all");
    }

    @Autowired
    FilmsRepository repository;
    @Autowired
    GenresRepository genresRepository;
    @Autowired
    ActorsRepository actorsRepository;
    @Autowired
    DirectorsRepository directorsRepository;
}
