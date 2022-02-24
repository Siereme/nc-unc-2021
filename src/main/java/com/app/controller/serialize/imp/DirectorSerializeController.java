package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/directors")
public class DirectorSerializeController extends AbstractSerializeController<Director> {
    private final String filePath = "src/main/resources/database/Directors.json";

    @Autowired
    private DirectorsRepository directorsRepository;
    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    @PostConstruct
    protected void getRepository() {
        super.repository = directorsRepository;
    }

    @Override
    @PostConstruct
    protected void getFilePath() {
        super.filePath = filePath;
    }

    @Override
    protected TypeReference<List<Director>> getRef() {
        return new TypeReference<List<Director>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/directors";
    }

    @Override
    protected List<String> checkErrors(List<Director> directorList) {
        List<String> errors = new LinkedList<>();

        List<Film> deserializeFilms = new LinkedList<>();

        directorList.forEach(film -> deserializeFilms.addAll(film.getFilms()));

        List<Integer> filmIds = getEntityIds(deserializeFilms);
        List<Film> checkFilms = filmsRepository.find(filmIds);

        if(filmIds.size() != checkFilms.size()){
            List<String> errorFilmsMessages = getErrorMessages(filmIds, deserializeFilms, checkFilms);
            errors.addAll(errorFilmsMessages);
        }
        return errors;
    }
}
