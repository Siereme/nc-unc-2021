package com.gmail.viktor.yuryev.mvc.console.controller;

import com.gmail.viktor.yuryev.mvc.console.model.Film;
import com.gmail.viktor.yuryev.mvc.console.repository.FilmsRepository;

import java.util.List;

public class FilmController {
    private FilmsRepository repository = new FilmsRepository();

    public List<Film> getFilms() {
        return repository.findAll();
    }

    public void deleteById(Integer id){
        repository.deleteById(id);
    }
}
