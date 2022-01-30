package com.example.app.model;

import com.example.app.model.film.Film;

import java.util.List;

public interface IParticipatesFilm extends IEntity {
    List<Integer> getFilms();
}
