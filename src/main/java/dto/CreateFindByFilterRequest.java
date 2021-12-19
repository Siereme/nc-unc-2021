package dto;

import java.util.LinkedList;

public class CreateFindByFilterRequest extends Request {
    public CreateFindByFilterRequest(String name) {
        super(name);
    }

    public CreateFindByFilterRequest(String name, LinkedList<String> actors, LinkedList<String> genres,
                                     LinkedList<String> directors) {
        super(name);
        actorsId = actors;
        genresId = genres;
        directorsId = directors;
    }

    private LinkedList<String> actorsId;
    private LinkedList<String> genresId;
    private LinkedList<String> directorsId;

    public String toString() {
        return "CreateFindByFilterRequest: request " + actorsId + genresId + directorsId;
    }

}
