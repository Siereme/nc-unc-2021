package dto;

import java.util.LinkedList;

public class CreateFindByFilterRequest extends Request {

    public CreateFindByFilterRequest(LinkedList<String> actors, LinkedList<String> genres,
                                     LinkedList<String> directors) {
        actorsId = actors;
        genresId = genres;
        directorsId = directors;
    }

    public LinkedList<String> getActorsId() {
        return actorsId;
    }

    private LinkedList<String> actorsId;


    public LinkedList<String> getGenresId() {
        return genresId;
    }

    private LinkedList<String> genresId;

    public LinkedList<String> getDirectorsId() {
        return directorsId;
    }

    private LinkedList<String> directorsId;

    public String toString() {
        return "CreateFindByFilterRequest: request " + actorsId + genresId + directorsId;
    }

}
