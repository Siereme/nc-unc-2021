package controller;

import model.Director.Director;
import model.Film.Film;
import repository.DirectorRepository;

import java.util.LinkedList;

public class DirectorController {

    public DirectorRepository getDirectorRepository() {
        return directorRepository;
    }

    public void setDirectorRepository(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    private DirectorRepository directorRepository = new DirectorRepository();

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

    public void setFilms(int ind, LinkedList<Film> newFilms) {
        directorRepository.findAll().get(ind).setFilms(newFilms);
    }

    public static String tittlesToString(LinkedList<Director> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directors) {
            sb.append(ind).append(". ").append(director.getName()).append("\n");
            ind++;
        }
        return new String(sb);
    }

}
