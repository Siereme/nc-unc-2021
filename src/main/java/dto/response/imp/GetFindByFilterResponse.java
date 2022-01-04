package dto.response.imp;

import app.model.IEntity;
import app.model.film.Film;
import dto.response.Response;

import java.util.LinkedList;
import java.util.List;

/** request send list of films to client */
public class GetFindByFilterResponse extends Response {

    public LinkedList<? extends IEntity> getEntities() {
        return entities;
    }

    private final LinkedList<IEntity> entities = new LinkedList<>();

    public GetFindByFilterResponse(String name, List<? extends IEntity> newEntities) {
        super(name);
        entities.addAll(newEntities);
    }

    public String toString() {
        if (entities.isEmpty()) {
            return "not found";
        } else {
            return "films found!";
        }
    }

}
