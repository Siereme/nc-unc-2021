package dto.response.imp;

import app.model.IEntity;
import dto.response.Response;

import java.util.LinkedList;
import java.util.List;

public class GetSearchEntityResponse extends Response {
    private final LinkedList<IEntity> entities = new LinkedList<>();

    public LinkedList<? extends IEntity> getEntities() {
        return entities;
    }

    public GetSearchEntityResponse(String name, List<? extends IEntity> newEntities) {
        super(name);
        entities.addAll(newEntities);
    }

    public String toString() {
        if (entities.isEmpty()) {
            return "GetSearchEntityResponse: not found entities";
        } else {
            return "GetSearchEntityResponse: entities found!";
        }
    }

}
