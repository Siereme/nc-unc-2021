package controller;

import model.Film.Film;
import repository.FilmsRepository;

import java.util.LinkedList;
import java.util.Objects;

public class FilmController {

    private FilmsRepository repository = new FilmsRepository();

    public FilmController() {
        repository = new FilmsRepository();
    }

    public FilmController(FilmsRepository newRepository) {
        repository = newRepository;
    }

    public FilmsRepository getRepository() {
        return repository;
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

/*    public boolean isContainsGenre(Genre genreToFind, Film film) {
        for (Genre genre : film.getGenres()) {
            if (genre.equals(genreToFind)) {
                return true;
            }
        }
        return false;
    }*/

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
        repository.findAll().get(filmInd).setGenres(newGenres); // выбрасывает ошибку, странно копируется(
        // repository.findAll().get(filmInd).getGenres().removeAll(newGenres);
    }

    public void setDirectors(int filmInd, LinkedList<String> newDirectors) {
        // repository.findAll().get(filmInd).setDirectors(newDirectors);
    }

    public void setActors(int filmInd, LinkedList<String> newActors) {
        repository.findAll().get(filmInd).setActors(newActors);
    }

    public static String tittlesToString(LinkedList<Film> films) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Film film : films) {
            sb.append(ind).append(". ").append(film.getTittle()).append("\n");
            ind++;
        }
        return new String(sb);
    }

    public Film findById(String id) {
        return (Film) repository.findAll().stream().dropWhile(f -> Objects.equals(f.getId(), id));
    }

    public String filmToString(Film film) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(film.getId()).append("\n").append("Tittle: ").append(film.getTittle()).append("\n")
                .append("Date of release: ").append(film.getDate()).append("\n");
        if (!film.getGenres().isEmpty()) {
            System.out.println(film.getGenres());
            sb.append("Genres\n");
            GenreController genreController = new GenreController();
            sb.append(genreController.genresById(film.getGenres())).append("\n");
        } else {
            sb.append("Genres is empty\n");
        }
        if (!film.getDirectors().isEmpty()) {
            DirectorController directorController = new DirectorController();
            sb.append(directorController.directorsById(film.getDirectors())).append("\n");
        } else {
            sb.append("Directors is empty\n");
        }
        if (!film.getActors().isEmpty()) {
            ActorController actorController = new ActorController();
            sb.append(actorController.actorsById(film.getActors())).append("\n");
        } else {
            sb.append("Actors is empty\n");
        }
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < repository.findAll().size(); ++i) {
            sb.append(i).append(". ").append(filmToString(repository.findAll().get(i))).append("\n");
        }
        return new String(sb);
    }

    public String filmsById(LinkedList<String> films) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : films) {
            sb.append(ind).append(". ").append(filmToString(findById(s))).append("\n");
        }
        return new String(sb);
    }

}
