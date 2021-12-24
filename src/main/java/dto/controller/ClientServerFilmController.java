package dto.controller;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.FilmController;
import app.controller.GenreController;
import app.model.film.Film;
import dto.request.AddFilmRequest;
import dto.request.EditFilmRequest;
import dto.request.FindByFilterRequest;

import java.util.Date;
import java.util.LinkedList;

public class ClientServerFilmController {
    FilmController filmController = new FilmController();
    ActorController actorController = new ActorController();
    DirectorController directorController = new DirectorController();
    GenreController genreController = new GenreController();

    public boolean editFilm(EditFilmRequest editFilmRequest) {
        String id = editFilmRequest.getFilmId();
        String tittle = editFilmRequest.getFilmName();
        Date date = editFilmRequest.getFilmDate();
        LinkedList<String> actors = editFilmRequest.getFilmActors();
        LinkedList<String> directors = editFilmRequest.getFilmDirectors();
        LinkedList<String> genres = editFilmRequest.getFilmGenres();
        return filmController.edit(id, tittle, date, actors, directors, genres);
    }

    public boolean addFilm(AddFilmRequest addFilmRequest) {
        String filmTittle = addFilmRequest.getFilmName();
        Date filmDate = addFilmRequest.getFilmDate();
        LinkedList<String> genres = addFilmRequest.getFilmGenres();
        LinkedList<String> actors = addFilmRequest.getFilmActors();
        LinkedList<String> directors = addFilmRequest.getFilmDirectors();
        Film film = new Film(filmTittle, filmDate, genres, directors, actors);
        filmController.addEntity(film);
        return true;
    }

    public LinkedList<Film> getFilmsByFilter(FindByFilterRequest findByFilterRequest) {
        LinkedList<String> actorNames = findByFilterRequest.getActors();
        LinkedList<String> actorIds = actorController.getIdsByNames(actorNames);

        LinkedList<String> directorNames = findByFilterRequest.getDirectors();
        LinkedList<String> directorsIds = directorController.getIdsByNames(directorNames);

        LinkedList<String> genreNames = findByFilterRequest.getGenres();
        LinkedList<String> genresIds = genreController.getIdsByNames(genreNames);

        return filmController.filmsBy(actorIds, genresIds, directorsIds);
    }

}
