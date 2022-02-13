package com.example.app.controller;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import com.example.app.repository.ActorsRepository;
import com.example.app.repository.DirectorsRepository;
import com.example.app.repository.FilmsRepository;
import com.example.app.repository.GenresRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@Controller
@RequestMapping(path = "/genres")
public class GenreController {
    private final Logger logger = Logger.getLogger(FilmController.class.getName());


    @GetMapping(value = "/all")
    public String get (ModelMap model){
        List<Genre> genres = repository.findAll();
        List<List<Film>> films = genres.stream().map(genre -> filmsRepository.find(genre.getFilms())).collect(Collectors.toList());

        model.addAttribute("genres", genres);
        model.addAttribute("films", films);
        model.addAttribute("json", "../serialize/genres");
        logger.info("Show all films");
        return "genres";
    }

    @PostMapping(value="/find")
    public ModelAndView get (@RequestParam @NotBlank String tittle, ModelMap model){
        List<Genre> genres = repository.findByContains(tittle);

        if(genres.size() > 0){
            List<List<Film>> films = genres.stream().map(genre -> filmsRepository.find(genre.getFilms())).collect(Collectors.toList());
            model.addAttribute("genres", genres);
            model.addAttribute("films", films);
            model.addAttribute("json", "../serialize/genres");
            return new ModelAndView("genres", model);
        }
        return new ModelAndView("redirect:/genres/all");
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Genre genre, ModelMap model, @Valid @PathVariable String commandType){
        List<Film> filmList = filmsRepository.findAll();
        if(Objects.equals(commandType, "page-add")){
            model.addAttribute("filmList", filmList);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "add");
        }
        if(Objects.equals(commandType, "page-edit")){;
            List<Film> filmGenreList = filmsRepository.findByGenres(Collections.singletonList(genre.getId()));

            filmList.removeIf(film -> filmGenreList.stream().anyMatch(filmGenre -> filmGenre.getId() == film.getId()));

            model.addAttribute("filmGenreList", filmGenreList);
            model.addAttribute("filmList", filmList);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "edit");
            model.addAttribute("genre", genre);
        }
        return "genre-handle";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@Valid @PathVariable int id){
        repository.delete(id);
        return new ModelAndView("redirect:/genres/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute Genre genre, BindingResult result, ModelMap map) {
        if(result.hasErrors()){
            map.addAttribute("result", result);
            return renderHandlePage(genre, map, "page-add");
        }
        repository.add(genre);
        return "redirect:/genres/all";
    }


    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute Genre genre, BindingResult result, ModelMap map) {
        if(result.hasErrors()){
            map.addAttribute("result", result);
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
