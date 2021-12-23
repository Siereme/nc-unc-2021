package dto.request;

import java.util.LinkedList;

/** request send list of actors name, list of genres name, list of directors name to server */
public class CreateFindByFilterRequest extends Request {

    public CreateFindByFilterRequest(LinkedList<String> actors, LinkedList<String> genres,
                                     LinkedList<String> directors) {
        this.actors = actors;
        this.genres = genres;
        this.directors = directors;
    }

    public LinkedList<String> getActors() {
        return actors;
    }

    private final LinkedList<String> actors;


    public LinkedList<String> getGenres() {
        return genres;
    }

    private final LinkedList<String> genres;

    public LinkedList<String> getDirectors() {
        return directors;
    }

    private final LinkedList<String> directors;

    public String toString() {
        return "CreateFindByFilterRequest: request " + actors + genres + directors;
    }

}
