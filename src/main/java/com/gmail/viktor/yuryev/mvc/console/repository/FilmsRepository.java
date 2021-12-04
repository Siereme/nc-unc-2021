package com.gmail.viktor.yuryev.mvc.console.repository;

import com.gmail.viktor.yuryev.mvc.console.model.Director;
import com.gmail.viktor.yuryev.mvc.console.model.Film;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO will be replaced by DB later
public class FilmsRepository implements IRepository<Film>{
    private final List<Film> films = new ArrayList<>();

    public FilmsRepository (){
        init();
    }

    //todo serrialization
    private void init() {
        final Director director = new Director("John", "Snow");
        films.add(new Film("MyFilm1", Arrays.asList(director)));
        films.add(new Film("MyFilm2", Arrays.asList(director)));
    }

    public List<Film> findAll() {
        return films;
    }


    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return false;
    }

    @Override
    public boolean create(Film entity) {
        return false;
    }

}
