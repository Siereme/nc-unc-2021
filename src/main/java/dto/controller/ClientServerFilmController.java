package dto.controller;

import app.controller.FilmController;
import app.model.film.Film;
import dto.request.CreateAddFilmRequest;
import dto.request.CreateEditFilmRequest;
import dto.request.CreateFindByFilterRequest;

import java.util.Date;
import java.util.LinkedList;

public class ClientServerFilmController {
    FilmController filmController = new FilmController();

    public boolean editFilm(CreateEditFilmRequest editFilmRequest) {
        String id = editFilmRequest.getFilmId();
        String tittle = editFilmRequest.getFilmName();
        Date date = editFilmRequest.getFilmDate();
        LinkedList<String> actors = editFilmRequest.getFilmActors();
        LinkedList<String> directors = editFilmRequest.getFilmDirectors();
        LinkedList<String> genres = editFilmRequest.getFilmGenres();
        return filmController.edit(id, tittle, date, actors, directors, genres);
    }

    public boolean addFilm(CreateAddFilmRequest addFilmRequest) {
        String filmTittle = addFilmRequest.getFilmName();
        Date filmDate = addFilmRequest.getFilmDate();
        LinkedList<String> genres = addFilmRequest.getFilmGenres();
        LinkedList<String> actors = addFilmRequest.getFilmActors();
        LinkedList<String> directors = addFilmRequest.getFilmDirectors();
        Film film = new Film(filmTittle, filmDate, genres, directors, actors);
        filmController.addEntity(film);
        return true;
    }

    public LinkedList<Film> getFilmsByFilter(CreateFindByFilterRequest findByFilterRequest) {
        LinkedList<String> actorNames = findByFilterRequest.getActors();
        LinkedList<String> actorIds = filmController.getIdsByNames(actorNames);

        LinkedList<String> directorNames = findByFilterRequest.getDirectors();
        LinkedList<String> directorsIds = filmController.getIdsByNames(directorNames);

        LinkedList<String> genreNames = findByFilterRequest.getGenres();
        LinkedList<String> genresIds = filmController.getIdsByNames(genreNames);

        return filmController.filmsBy(actorIds, genresIds, directorsIds);
    }

}
