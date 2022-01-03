package dto.response.imp;

import app.model.IEntity;
import dto.response.Response;

import java.util.LinkedList;
import java.util.List;

public class GetEntitiesResponse extends Response {
    public List<? extends IEntity> getEntities() {
        return entities;
    }

    private final List<? extends IEntity> entities;

    public GetEntitiesResponse(String name, List<? extends IEntity> entities) {
        super(name);
        this.entities = entities;
    }

    public String toString() {
        return "GetEntitiesByNamesResponse" + entities;
    }

}
