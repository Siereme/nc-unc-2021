package controller;

import model.Film.Film;
import repository.DirectorRepository;

public class DirectorController {
    private DirectorRepository directorRepository;

    // directorInd - индекс директора, к которому добавляем фильм
    public void addFilm(Film film, int directorInd) {
        directorRepository.findAll().get(directorInd).getFilms().add(film);
    }

    // directorInd - индекс директора, к которому добавляем фильм
    // filmInd - индекс фильма, который заменяем на новый
    public void setFilm(int directorInd, int filmInd, Film film) {
        directorRepository.findAll().get(directorInd).getFilms().set(filmInd, film);
    }

    // directorInd - индекс директора, к которому добавляем фильм
    // filmInd - индекс фильма, который заменяем на новый
    public void removeFilm(int directorInd, int filmInd) {
        directorRepository.findAll().get(directorInd).getFilms().remove(filmInd);
    }

}
