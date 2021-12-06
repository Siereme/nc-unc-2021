package controller;

import model.Director.Director;
import repository.DirectorRepository;

import java.util.LinkedList;
import java.util.Objects;

public class DirectorController {

    public DirectorRepository getDirectorRepository() {
        return directorRepository;
    }

    public void setDirectorRepository(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    private DirectorRepository directorRepository = new DirectorRepository();

    // directorInd - индекс директора, к которому добавляем фильм
/*    public void addFilm(Film film, int directorInd) {
        directorRepository.findAll().get(directorInd).getFilms().add(film);
    }*/

    // directorInd - индекс директора, к которому добавляем фильм
    // filmInd - индекс фильма, который заменяем на новый
/*    public void setFilm(int directorInd, int filmInd, Film film) {
        directorRepository.findAll().get(directorInd).getFilms().set(filmInd, film);
    }*/

    // directorInd - индекс директора, к которому добавляем фильм
    // filmInd - индекс фильма, который заменяем на новый
    public void removeFilm(int directorInd, int filmInd) {
        directorRepository.findAll().get(directorInd).getFilms().remove(filmInd);
    }

    public void setFilms(int ind, LinkedList<String> newFilms) {
        directorRepository.findAll().get(ind).setFilms(newFilms);
    }

/*    public static String tittlesToString(LinkedList<Director> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directors) {
            sb.append(ind).append(". ").append(director.getName()).append("\n");
            ind++;
        }
        return new String(sb);
    }*/

    public Director directorById(String id) {
        for (Director director : directorRepository.findAll()) {
            if (Objects.equals(director.getId(), id)) {
                return director;
            }
        }
        return null;
    }

    public String directorToString(Director director) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(director.getId()).append("\n");
        sb.append("Name: ").append(director.getName()).append("\n");
        sb.append("Year: ").append(director.getYear()).append("\n");
        if (director.getFilms().isEmpty()) {
            sb.append("Films is empty\n");
        } else {
            FilmController filmController = new FilmController();
            sb.append(filmController.filmsByIdToString(director.getFilms())).append("\n");
        }
        return new String(sb);

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directorRepository.findAll()) {
            sb.append(ind++).append(". ").append(directorToString(director)).append("\n");
        }
        return new String(sb);
    }

    public String directorsByIdToString(LinkedList<String> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String d : directors) {
            sb.append(ind++).append(". ").append(directorToString(directorById(d))).append("\n");
        }
        return new String(sb);
    }

}
