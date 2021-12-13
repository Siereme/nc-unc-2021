package controller;

import model.IEntity;
import model.director.Director;
import model.film.Film;
import repository.DirectorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Контроллер для сущности режиссер
 * @see IEntityController
 * @see Director
 * @see DirectorRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class DirectorController implements IEntityController<Director> {

    public DirectorRepository getDirectorRepository() {
        return directorRepository;
    }

    public void setDirectorRepository(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    private DirectorRepository directorRepository = new DirectorRepository();

    public Director getEntityById(String id) {
        for (Director director : directorRepository.findAll()) {
            if (Objects.equals(director.getId(), id)) {
                return director;
            }
        }
        return null;
    }

    public String entityToString(Director director) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(director.getId()).append("\n");
        sb.append("Name: ").append(director.getName()).append("\n");
        sb.append("Year: ").append(director.getYear()).append("\n");
        if (director.getFilms().isEmpty()) {
            sb.append("Films is empty\n");
        } else {
            FilmController filmController = new FilmController();
            sb.append(filmController.entitiesByIDsToString(director.getFilms())).append("\n");
        }
        return new String(sb);

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directorRepository.findAll()) {
            sb.append(ind++).append(". ").append(entityToString(director)).append("\n");
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String d : directors) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(d))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return directorRepository.size();
    }

    public Director getEntity(int ind) {
        return directorRepository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        directorRepository.findAll().add((Director) entity);
    }

    public void updateRepository() {
        try {
            directorRepository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        FilmController filmController = new FilmController();
        Director director = getEntity(ind);
        filmController.removeDirectorFromAllFilms(director);
        filmController.updateRepository();
        directorRepository.findAll().remove(ind);
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Director director : directorRepository.findAll()) {
            ids.add(director.getId());
        }
        return ids;
    }

    public void addFilmToDirectors(Film film, LinkedList<String> directorsId) {
        for (String dirId : directorsId) {
            Director director = getEntityById(dirId);
            LinkedList<String> dirFilmsID = director.getFilms();
            dirFilmsID.add(film.getId());
        }
    }

    public void removeFilmFromAllDirectors(Film film) {
        for (Director director : directorRepository.findAll()) {
            if (isContainsFilm(director, film)) {
                LinkedList<String> filmsId = director.getFilms();
                filmsId.remove(film.getId());
            }
        }
    }

    public static boolean isContainsFilm(Director director, Film film) {
        for (String filmID : director.getFilms()) {
            if (Objects.equals(film.getId(), filmID)) {
                return true;
            }
        }
        return false;
    }

}
