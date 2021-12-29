package app.controller.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.repository.imp.FilmsRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Film app.controller
 * @see Film
 * @see IEntityController
 * @see FilmsRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class FilmController implements IEntityController<Film> {

    private final FilmsRepository repository;

    public FilmController() {
        repository = new FilmsRepository();
    }

    public FilmController(FilmsRepository newRepository) {
        repository = newRepository;
    }

    public FilmsRepository getRepository() {
        return repository;
    }

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (Film film : repository.findAll()) {
            sb.append(film.getTittle()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Film film : repository.findAll()) {
            sb.append(ind++).append(". ");
            sb.append(entityToString(film));
            sb.append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getIdByName(String name) {
        for (Film film : repository.findAll()) {
            String filmName = film.getTittle();
            if (Objects.equals(filmName, name)) {
                return film.getId();
            }
        }
        return null;
    }

    @Override
    public boolean remove(String id) {
        Film film = getEntityById(id);
        repository.findAll().remove(film);
        return updateRepository();
    }

    @Override
    public boolean remove(Film entity) {
        repository.findAll().remove(entity);
        return updateRepository();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
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
        for (Film film : repository.findAll()) {
            if (Objects.equals(film.getId(), id)) {
                return film;
            }
        }
        return null;
    }

    @Override
    public Film getEntityByName(String title) {
        for (Film film : repository.findAll()) {
            if (Objects.equals(film.getTittle(), title)) {
                return film;
            }
        }
        return null;
    }

    public String entityToString(Film film) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(film.getId()).append(" ");
        sb.append("Tittle: ").append(film.getTittle()).append("\n");
        if (!film.getGenres().isEmpty()) {
            sb.append("Genres\n");
            GenreController genreController = new GenreController();
            for (String genreId : film.getGenres()) {
                Genre genre = genreController.getEntityById(genreId);
                String genreTittle = genre.getTittle();
                sb.append(genreTittle).append(" ");
            }
            sb.append("\n");
        } else {
            sb.append("Genres is empty\n");
        }
        if (!film.getDirectors().isEmpty()) {
            sb.append("Directors\n");
            DirectorController directorController = new DirectorController();
            for (String dirId : film.getDirectors()) {
                Director director = directorController.getEntityById(dirId);
                String directorName = director.getName();
                sb.append(directorName).append(" ");
            }
            sb.append("\n");
        } else {
            sb.append("Directors is empty\n");
        }
        if (!film.getActors().isEmpty()) {
            sb.append("Actors\n");
            ActorController actorController = new ActorController();
            for (String actorId : film.getActors()) {
                Actor actor = actorController.getEntityById(actorId);
                String actorName = actor.getName();
                sb.append(actorName).append(" ");
            }
            sb.append("\n");
        } else {
            sb.append("Actors is empty\n");
        }
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < repository.findAll().size(); ++i) {
            sb.append(i).append(". ").append(entityToString(repository.findAll().get(i))).append("\n");
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

    /** Метод проверки, содержится ли автор в фильме
     * @param film - фильм для которого будет проверяться, содержится ли в нем определенный актер
     * @param actorId - id актера, которого будет искать метод в фильме film
     * @return true - если в фильме film был найден актер @actor, false - иначе
     * */
    public static boolean isContainsActor(Film film, String actorId) {
        for (String actorID : film.getActors()) {
            if (Objects.equals(actorID, actorId)) {
                return true;
            }
        }
        return false;
    }

    /** Метод получает список id фильмов, в которых участвует актер @actor
     * @param actor - актер, по которому будут найдены все фильмы с его участием
     * @return список id фильмов, в которых участвует заданный актер @actor
     * */
    public LinkedList<String> getFilmsByActor(Actor actor) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : repository.findAll()) {
            if (isContainsActor(film, actor.getId())) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    /** Метод проверки, содержится ли автор в фильме
     * @param film - фильм для которого будет проверяться, содержится ли в нем определенный режиссер
     * @param idDirector - id режиссера, которого будет искать метод в фильме film
     * @return true - если в фильме film был найден актер @director, false - иначе
     * */
    public static boolean isContainsDirector(Film film, String idDirector) {
        for (String dirId : film.getDirectors()) {
            if (Objects.equals(dirId, idDirector)) {
                return true;
            }
        }
        return false;
    }

    /** Метод получает список id фильмов, в которых участвует актер @actor
     * @param director - режиссер, по которому будут найдены все фильмы с его участием
     * @return список id фильмов, в которых участвует заданный режиссер @director
     * */
    public LinkedList<String> getFilmsByDirector(Director director) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : repository.findAll()) {
            if (isContainsDirector(film, director.getId())) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    /** Метод проверки, содержится ли жанр в фильме
     * @param film - фильм для которого будет проверяться, содержится ли в нем определенный жанр
     * @param idGenre - жанр, по которому будет проверяться фильм @film
     * @return true - если в фильме film был найден жанр @genre, false - иначе
     * */
    public boolean isContainsGenre(Film film, String idGenre) {
        for (String genreId : film.getGenres()) {
            if (Objects.equals(genreId, idGenre)) {
                return true;
            }
        }
        return false;
    }

    /** Метод получает список id фильмов, у которых список жанров содержит жанр @genre
     * @param genre - жанр, по которому будут найдены все фильмы
     * @return список id фильмов, у которых список жанров содержит жанр @genre
     * */
    public LinkedList<String> getFilmsByGenre(Genre genre) {
        LinkedList<String> filmsId = new LinkedList<>();
        for (Film film : repository.findAll()) {
            if (isContainsGenre(film, genre.getId())) {
                filmsId.add(film.getId());
            }
        }
        return filmsId;
    }

    public int size() {
        return repository.size();
    }

    public Film getEntity(int ind) {
        return repository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        Film film = new Film((Film) entity);
        repository.findAll().add(film);
        updateRepository();
    }

    public boolean updateRepository() {
        try {
            repository.serialize();
            return true;
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
            return false;
        }
    }

    public void removeEntity(int ind) {
        Film film = getEntity(ind);
        ActorController actorController = new ActorController();
        DirectorController directorController = new DirectorController();
        // remove film from film app.repository
        repository.findAll().remove(ind);
        updateRepository();
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Film film : repository.findAll()) {
            ids.add(film.getId());
        }
        return ids;
    }

    public void addActorToFilms(Actor actor, LinkedList<String> ids) {
        for (String id : ids) {
            Film film = getEntityById(id);
            List<String> actorsId = film.getActors();
            actorsId.add(actor.getId());
        }
        updateRepository();
    }

    public void addDirectorToFilms(Director director, LinkedList<String> ids) {
        for (String id : ids) {
            Film film = getEntityById(id);
            List<String> directorsId = film.getDirectors();
            directorsId.add(director.getId());
        }
        updateRepository();
    }

    public void removeActorFromAllFilms(Actor actor) {
        boolean isChange = false;
        for (Film film : repository.findAll()) {
            if (isContainsActor(film, actor.getId())) {
                removeActorFromFilm(actor, film);
                isChange = true;
            }
        }
        if (isChange) {
            updateRepository();
        }
    }

    public static void addActorToFilm(Actor actor, Film film) {
        List<String> actorsId = film.getActors();
        actorsId.add(actor.getId());
    }

    public static void removeActorFromFilm(Actor actor, Film film) {
        List<String> actorsId = film.getActors();
        actorsId.remove(actor.getId());
    }

    public static void removeDirectorFromFilm(Director director, Film film) {
        List<String> directorsId = film.getDirectors();
        directorsId.remove(director.getId());
    }

    public void removeDirectorFromAllFilms(Director director) {
        boolean isChange = false;
        for (Film film : repository.findAll()) {
            if (isContainsDirector(film, director.getId())) {
                removeDirectorFromFilm(director, film);
                isChange = true;
            }
        }
        if (isChange) {
            updateRepository();
        }
    }

    // function may be parallel
    public LinkedList<Film> filmsBy(LinkedList<String> actorsId, LinkedList<String> genresId,
                                    LinkedList<String> directorsId) {
        LinkedList<Film> films = new LinkedList<>();
        for (Film film : this.getRepository().findAll()) {
            boolean flag = true;
            for (String actorId : actorsId) {
                if (!isContainsActor(film, actorId)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (String genreId : genresId) {
                    if (!isContainsGenre(film, genreId)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                for (String directorId : directorsId) {
                    if (!isContainsDirector(film, directorId)) {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag) {
                films.add(film);
            }
        }
        return films;
    }

    public boolean edit(Film editFilm) {
        Film film = getEntityById(editFilm.getId());
        film.setTittle(editFilm.getTittle());
        film.setDate(editFilm.getDate());
        film.setActors(editFilm.getActors());
        film.setDirectors(editFilm.getDirectors());
        film.setGenres(editFilm.getGenres());
        return updateRepository();
    }

}
