package com.example.app.controller;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import com.example.app.repository.ActorsRepository;
import com.example.app.repository.FilmsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/actors")
public class ActorController {
    private final Logger logger = Logger.getLogger(ActorController.class.getName());

    @Autowired
    private ActorsRepository repository = new ActorsRepository();

    @Autowired
    private FilmsRepository filmsRepository = new FilmsRepository();

    @GetMapping(value = "/all")
    public String get(ModelMap model) {
        List<Actor> actorList = repository.findAll();
        model.addAttribute("actors", actorList);
        List<List<Film>> listListFilms = new LinkedList<>();
        for (Actor actor : actorList) {
            Integer id = actor.getId();
            List<Film> films = repository.findFilmsByActorId(id);
            listListFilms.add(films);
        }
        model.addAttribute("films", listListFilms);
        logger.info("show all actors");
        return "actors";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        repository.delete(id);
        return new ModelAndView("redirect:/actors/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam String tittle, ModelMap model) {
        List<Actor> actorList = repository.findByName(tittle);
        if (actorList == null || actorList.isEmpty()) {
            return new ModelAndView("redirect:/actors/all");
        } else {
            model.addAttribute("actors", actorList);
            List<List<Film>> listListFilms = new LinkedList<>();
            for (Actor actor1 : actorList) {
                Integer id = actor1.getId();
                List<Film> films = repository.findFilmsByActorId(id);
                listListFilms.add(films);
            }
            model.addAttribute("films", listListFilms);
            logger.info("show all actors");
            return new ModelAndView("actors", model);
        }
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute Actor actor, ModelMap model, @PathVariable String commandType) {
        List<Film> films = filmsRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute("filmList", films);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            List<Film> actorFilmList = repository.findFilmsByActorId(actor.getId());
            model.addAttribute("filmList", films);
            model.addAttribute("actorFilmList", actorFilmList);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "/handle/edit");
            model.addAttribute("actor", actor);
        }
        return "actor-handle";
    }

    @PostMapping(value = "/handle/add")
    public ModelAndView add(@ModelAttribute Actor actor) {
        repository.add(actor);
        return new ModelAndView("redirect:/actors/all");
    }

    @PostMapping(value = "/handle/edit")
    public ModelAndView edit(@ModelAttribute Actor actor){
        repository.edit(actor);
        return new ModelAndView("redirect:/actors/all");
    }

}
