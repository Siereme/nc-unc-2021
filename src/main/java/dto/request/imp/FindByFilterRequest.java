package dto.request.imp;

import app.model.IEntity;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import dto.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** request send list of actors name, list of genres name, list of directors name to server */
public class FindByFilterRequest extends Request {

    public FindByFilterRequest(Map<String, List<String>> entities, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        if(entities.containsKey("film")){
            List<String> filmIds = entities.get("film");
            this.films.addAll(filmIds);
        }
        if(entities.containsKey("actor")){
            List<String> actorIds = entities.get("actor");
            this.actors.addAll(actorIds);
        }
        if(entities.containsKey("genre")){
            List<String> genreIds = entities.get("genre");
            this.genres.addAll(genreIds);
        }
        if(entities.containsKey("director")){
            List<String> directorIds = entities.get("director");
            this.directors.addAll(directorIds);
        }
    }


    public LinkedList<String> getActors() {
        return actors;
    }

    private final LinkedList<String> actors = new LinkedList<>();


    public LinkedList<String> getGenres() {
        return genres;
    }

    private final LinkedList<String> genres = new LinkedList<>();

    public LinkedList<String> getDirectors() {
        return directors;
    }

    private final LinkedList<String> directors = new LinkedList<>();

    public LinkedList<String> getFilms() {
        return films;
    }

    private final LinkedList<String> films = new LinkedList<>();

    public String toString() {
        return "CreateFindByFilterRequest: request " + actors + genres + directors;
    }

}
