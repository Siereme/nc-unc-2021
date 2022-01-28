package com.example.app.controller;

import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.repository.DirectorsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(path = "/directors")
public class DirectorController {
    private final Logger logger = Logger.getLogger(DirectorController.class.getName());

    @Autowired
    private DirectorsRepository repository = new DirectorsRepository();

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
        logger.info("show all directors");
        return "directors";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/directors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam String tittle, ModelMap model) {
        List<Director> directorList = repository.findByName(tittle);
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
            logger.info("show all directors");
            return new ModelAndView("directors", model);
        }
    }


}
