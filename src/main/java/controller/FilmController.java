package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Actor.Actor;
import model.Director.Director;
import model.Film.Film;
import model.Genre.Genre;
import repository.FilmsRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class FilmController implements IController<Film> {

    private FilmsRepository filmsRepository;

    public FilmController() {
        filmsRepository = new FilmsRepository();
    }

    public FilmController(FilmsRepository newRepository) {
        filmsRepository = newRepository;
    }

    public FilmsRepository getFilmsRepository() {
        return filmsRepository;
    }

    public void deleteById(Integer id) {
        filmsRepository.deleteById(id);
    }

    public void setGenres(int filmInd, LinkedList<String> newGenres) {
        Film film = getEntity(filmInd);
        film.setGenres(newGenres);
    }

    public void setDirectors(int filmInd, LinkedList<String> newDirectors) {
        Film film = getEntity(filmInd);
        film.setDirectors(newDirectors);
    }

    public void setActors(int filmInd, LinkedList<String> newActors) {
        Film film = getEntity(filmInd);
        film.setActors(newActors);
    }

    public Film getEntityById(String id) {
        for (Film film : filmsRepository.findAll()) {
            if (Objects.equals(film.getId(), id)) {
                return film;
            }
        }
        return null;
    }

    public String entityToString(Film film) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(film.getId()).append("\n").append("Tittle: ").append(film.getTittle()).append("\n")
                .append("Date of release: ").append(film.getDate()).append("\n");
        if (!film.getGenres().isEmpty()) {
            sb.append("Genres\n");
            GenreController genreController = new GenreController();
            sb.append(genreController.entitiesByIDsToString(film.getGenres())).append("\n");
        } else {
            sb.append("Genres is empty\n");
        }
        if (!film.getDirectors().isEmpty()) {
            sb.append("Directors\n");
            DirectorController directorController = new DirectorController();
            sb.append(directorController.entitiesByIDsToString(film.getDirectors())).append("\n");
        } else {
            sb.append("Directors is empty\n");
        }
        if (!film.getActors().isEmpty()) {
            sb.append("Actors\n");
            ActorController actorController = new ActorController();
            sb.append(actorController.entitiesByIDsToString(film.getActors())).append("\n");
        } else {
            sb.append("Actors is empty\n");
        }
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < filmsRepository.findAll().size(); ++i) {
            sb.append(i).append(". ").append(entityToString(filmsRepository.findAll().get(i))).append("\n");
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> films) {
        if (films.isEmpty()) {
            return "Films is empty!\n";
        }
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : films) {
            sb.append(ind).append(". ").append(entityToString(getEntityById(s))).append("\n");
        }
        return new String(sb);
    }

    public static boolean isContainsActor(Film film, Actor actor) {
        for (String actorID : film.getActors()) {
            if (Objects.equals(actorID, actor.getId())) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getFilmsByActor(Actor actor) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : filmsRepository.findAll()) {
            if (isContainsActor(film, actor)) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    public static boolean isContainsDirector(Film film, Director director) {
        for (String dirId : film.getDirectors()) {
            if (Objects.equals(dirId, film.getId())) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getFilmsByDirector(Director director) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : filmsRepository.findAll()) {
            if (isContainsDirector(film, director)) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    public boolean isContainsGenre(Film film, Genre genre) {
        for (String genreId : film.getGenres()) {
            if (Objects.equals(genreId, genre.getId())) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getFilmsByGenre(Genre genre) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : filmsRepository.findAll()) {
            if (isContainsGenre(film, genre)) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    public int size() {
        return filmsRepository.size();
    }

    public Film getEntity(int ind) {
        return filmsRepository.findAll().get(ind);
    }

    public void addEntity() {
        filmsRepository.findAll().add(new Film());
    }

    public void updateRepository() {
        try {
            filmsRepository.serialize(new ObjectMapper());
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        filmsRepository.findAll().remove(ind);
    }

}
