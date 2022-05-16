package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.app.repository.IRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/directors")
public class DirectorSerializeController extends AbstractSerializeController<Director> {

    @Autowired
    private DirectorsRepository directorsRepository;
    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    protected IRepository<Director> getRepository() {
//        return directorsRepository;
        return null;
    }

    @Override
    protected String getFilePath() {
        return "src/main/resources/database/Directors.json";
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

        List<Film> deserializeFilms = new LinkedList<>();

//        directorList.forEach(film -> deserializeFilms.addAll(film.getFilms()));

        List<Integer> filmIds = getEntityIds(deserializeFilms);
//        List<Film> checkFilms = filmsRepository.find(filmIds);

//        List<String> errorFilmsMessages = getErrorMessages(filmIds, deserializeFilms, checkFilms);

//        return new LinkedList<>(errorFilmsMessages);
        return null;
    }
}
