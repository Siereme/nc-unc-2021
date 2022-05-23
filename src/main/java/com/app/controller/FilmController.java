package com.app.controller;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.genre.Genre;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.app.model.film.Film;
import com.app.repository.ActorsRepository;
import com.app.repository.GenresRepository;
import com.app.service.SequenceGeneratorService;
import com.app.service.actor.ActorService;
import com.app.service.director.DirectorService;
import com.app.service.film.FilmService;
import com.app.service.genre.GenreService;
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
        getAll(model, films, genres, directors, actors);
        logger.info("Show all films");

        return "films";
    }

    private void getAll(ModelMap model, Collection<Film> films, Collection<Collection<Genre>> genres,
                        Collection<Collection<Director>> directors, Collection<Collection<Actor>> actors) {
        for (Film film : films) {
            Collection<Genre> genres1 = (Collection<Genre>) genresRepository.findAllById(film.getGenresIds());
            genres.add(genres1);
            Collection<Actor> actors1 = (Collection<Actor>) actorsRepository.findAllById(film.getActorsIds());
            actors.add(actors1);
            Collection<Director> directors1 =
                    (Collection<Director>) directorsRepository.findAllById(film.getDirectorsIds());
            directors.add(directors1);
        }
        model.addAttribute(FILMS, films);
        model.addAttribute(GENRES, genres);
        model.addAttribute(ACTORS, actors);
        model.addAttribute(DIRECTORS, directors);
        model.addAttribute(JSON, "../serialize/films");
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
        Collection<Film> findFilm = service.findByContains(tittle);
        if (findFilm.size() > 0) {
            Collection<Collection<Genre>> genres = new LinkedList<>();
            Collection<Collection<Actor>> actors = new LinkedList<>();
            Collection<Collection<Director>> directors = new LinkedList<>();
            getAll(model, findFilm, genres, directors, actors);
            return new ModelAndView(FILMS, model);
        }
        model.addAttribute(FILMS, findFilm);
        return new ModelAndView("films", model);
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Film film, ModelMap model,
                                   @PathVariable("commandType") @NotBlank String commandType) throws Exception {
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
            film = repository.findById(id).orElseThrow(() -> new Exception("Film is not found"));
            Collection<Integer> genreList = film.getGenresIds();
            Collection<Integer> actorList = film.getActorsIds();
            Collection<Integer> directorList = film.getDirectorsIds();

            if (genres.size() != 0) {
                genres.removeIf(genre -> genreList.stream().anyMatch(filmGenre -> filmGenre == genre.getId()));
            }
            if (actors.size() != 0) {
                actors.removeIf(actor -> actorList.stream().anyMatch(filmActor -> filmActor == actor.getId()));
            }
            if (directors.size() != 0) {
                directors.removeIf(
                        director -> directorList.stream().anyMatch(filmDirector -> filmDirector == director.getId()));
            }

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
    public ModelAndView delete(@PathVariable("id") @NotNull int id) throws Exception {
        Film film = repository.findById(id).orElseThrow(() -> new Exception("Film is not found"));
        genreService.removeFilmFromGenre(film);
        actorService.removeFilmFromActors(film);
        directorService.removeFilmFromDirectors(film);
        repository.deleteById(id);
        return new ModelAndView("redirect:/films/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map) throws Exception {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(film, map, "page-add");
        }
        film.setId(sequenceGeneratorService.generateSequence(Film.SEQUENCE_NAME));
        genreService.addFilmToGenres(film);
        actorService.addFilmToActors(film);
        directorService.addFilmToDirectors(film);
        repository.insert(film);
        return "redirect:/films/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map) throws Exception {
        if (result.hasErrors()) {
            map.addAttribute(RESULT, result);
            return renderHandlePage(film, map, "page-edit");
        }
        genreService.updateGenresByFilm(film);
        actorService.updateActorsByFilm(film);
        directorService.updateDirectorsByFilm(film);
        repository.save(film);
        return "redirect:/films/all";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/films/all");
    }

    @Autowired
    private FilmsRepository repository;
    @Autowired
    private FilmService service;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private ActorService actorService;
    @Autowired
    private DirectorsRepository directorsRepository;
    @Autowired
    private DirectorService directorService;
}
