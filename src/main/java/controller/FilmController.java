package controller;

import model.Actor.Actor;
import model.Director.Director;
import model.Film.Film;
import model.Genre.Genre;
import repository.FilmsRepository;

import java.util.LinkedList;
import java.util.Objects;

public class FilmController {

    private FilmsRepository filmsRepository = new FilmsRepository();

/*    // возможно этих полей не будет, после того как будет реализована сериализация/десериализация
    private ActorController actorController = new ActorController();
    private GenreController genreController = new GenreController();
    private DirectorController directorController = new DirectorController();*/

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

/*    public FilmsRepository getFilmsByGenre(Genre genre) {
        FilmsRepository filmsByGenre = new FilmsRepository();
        for (Film film : repository.findAll()) {
            if (isContainsGenre(genre, film)) {
                filmsByGenre.create(film);
            }
        }
        return filmsByGenre;
    }*/

    public void setGenres(int filmInd, LinkedList<String> newGenres) {
        Film film = getFilm(filmInd);
        film.setGenres(newGenres);
    }

    public void setDirectors(int filmInd, LinkedList<String> newDirectors) {
        Film film = getFilm(filmInd);
        film.setDirectors(newDirectors);
    }

    public void setActors(int filmInd, LinkedList<String> newActors) {
        Film film = getFilm(filmInd);
        film.setActors(newActors);
    }

    public Film getFilmById(String id) {
        for (Film film : filmsRepository.findAll()) {
            if (Objects.equals(film.getId(), id)) {
                return film;
            }
        }
        return null;
    }

    public String filmToString(Film film) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(film.getId()).append("\n").append("Tittle: ").append(film.getTittle()).append("\n")
                .append("Date of release: ").append(film.getDate()).append("\n");
        if (!film.getGenres().isEmpty()) {
            sb.append("Genres\n");
            GenreController genreController = new GenreController();
            sb.append(genreController.genresByIdToString(film.getGenres())).append("\n");
        } else {
            sb.append("Genres is empty\n");
        }
        if (!film.getDirectors().isEmpty()) {
            sb.append("Directors\n");
            DirectorController directorController = new DirectorController();
            sb.append(directorController.directorsByIdToString(film.getDirectors())).append("\n");
        } else {
            sb.append("Directors is empty\n");
        }
        if (!film.getActors().isEmpty()) {
            sb.append("Actors\n");
            ActorController actorController = new ActorController();
            sb.append(actorController.actorsByIdToString(film.getActors())).append("\n");
        } else {
            sb.append("Actors is empty\n");
        }
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < filmsRepository.findAll().size(); ++i) {
            sb.append(i).append(". ").append(filmToString(filmsRepository.findAll().get(i))).append("\n");
        }
        return new String(sb);
    }

    public String filmsByIdToString(LinkedList<String> films) {
        if (films.isEmpty()) {
            return "Films is empty!\n";
        }
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : films) {
            sb.append(ind).append(". ").append(filmToString(getFilmById(s))).append("\n");
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

    public Film getFilm(int ind) {
        return filmsRepository.findAll().get(ind);
    }

    public void addFilm(){
        filmsRepository.findAll().add(new Film());
    }

    public void updateRepository() {
        filmsRepository.init();
    }

}
