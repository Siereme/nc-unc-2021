package com.example.app.controller;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import com.example.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@Controller
@RequestMapping(path = "/films")
public class FilmController implements WebMvcConfigurer {
    private final Logger logger = Logger.getLogger(FilmController.class.getName());


    @GetMapping(value = "/all")
    public String get (ModelMap model){
        List<Film> films = repository.findAll();
        List<List<Genre>> genres = films.stream().map(film -> genresRepository.find(film.getGenres())).collect(Collectors.toList());
        List<List<Actor>> actors = films.stream().map(film -> actorsRepository.find(film.getActors())).collect(Collectors.toList());
        List<List<Director>> directors = films.stream().map(film -> directorsRepository.find(film.getDirectors())).collect(Collectors.toList());
        model.addAttribute("films", films);
        model.addAttribute("genres", genres);
        model.addAttribute("actors", actors);
        model.addAttribute("directors", directors);
        logger.info("Show all films");
        return "films";
    }

    @PostMapping(value="/find")
    public ModelAndView get (@RequestParam("tittle") @NotBlank String tittle, ModelMap model) throws ConstraintViolationException{
        List<Film> findFilm = repository.findByContains(tittle);
        if(findFilm.size() > 0){
            List<List<Genre>> genres = findFilm.stream().map(film -> genresRepository.find(film.getGenres())).collect(Collectors.toList());
            List<List<Actor>> actors = findFilm.stream().map(film -> actorsRepository.find(film.getActors())).collect(Collectors.toList());
            List<List<Director>> directors = findFilm.stream().map(film -> directorsRepository.find(film.getDirectors())).collect(Collectors.toList());
            model.addAttribute("films", findFilm);
            model.addAttribute("genres", genres);
            model.addAttribute("actors", actors);
            model.addAttribute("directors", directors);
            return new ModelAndView("films", model);
        }
        return new ModelAndView("redirect:/films/all");
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Film film, ModelMap model, @PathVariable("commandType") @NotBlank String commandType){
        List<Genre> genreList = genresRepository.findAll();
        List<Actor> actorList = actorsRepository.findAll();
        List<Director> directorList = directorsRepository.findAll();
        if(Objects.equals(commandType, "page-add")){
            model.addAttribute("genreList", genreList);
            model.addAttribute("actorList", actorList);
            model.addAttribute("directorList", directorList);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "add");
        }
        if(Objects.equals(commandType, "page-edit")){;
            List<Genre> genreFilmList = genresRepository.findByFilms(Collections.singletonList(film.getId()));
            List<Actor> actorFilmList = actorsRepository.findByFilms(Collections.singletonList(film.getId()));
            List<Director> directorFilmList = directorsRepository.findByFilms(Collections.singletonList(film.getId()));

            genreList.removeIf(genre -> genreFilmList.stream().anyMatch(filmGenre -> filmGenre.getId() == genre.getId()));
            actorList.removeIf(actor -> actorFilmList.stream().anyMatch(filmActor -> filmActor.getId() == actor.getId()));
            directorList.removeIf(director -> directorFilmList.stream().anyMatch(filmDirector -> filmDirector.getId() == director.getId()));

            model.addAttribute("genreFilmList", genreFilmList);
            model.addAttribute("actorFilmList", actorFilmList);
            model.addAttribute("directorFilmList", directorFilmList);
            model.addAttribute("genreList", genreList);
            model.addAttribute("actorList", actorList);
            model.addAttribute("directorList", directorList);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "edit");
            model.addAttribute("film", film);
        }
        return "film-handle";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable("id") @NotNull int id){
        repository.delete(id);
        return new ModelAndView("redirect:/films/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map){
        if(result.hasErrors()){
            map.addAttribute("result", result);
            return renderHandlePage(film, map, "page-add");
        }
        repository.add(film);
        return "redirect:/films/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Film film, BindingResult result, ModelMap map){
        if(result.hasErrors()){
            map.addAttribute("result", result);
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
    private FilmsRepository repository;
    @Autowired
    GenresRepository genresRepository;
    @Autowired
    ActorsRepository actorsRepository;
    @Autowired
    DirectorsRepository directorsRepository;
}
