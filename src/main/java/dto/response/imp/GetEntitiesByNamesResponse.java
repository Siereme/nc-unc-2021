package dto.response.imp;

import app.model.IEntity;
import dto.response.Response;

import java.util.LinkedList;

public class GetEntitiesByNamesResponse extends Response {
    public LinkedList<? extends IEntity> getEntities() {
        return entities;
    }

    private final LinkedList<? extends IEntity> entities;

    public GetEntitiesByNamesResponse(String name, LinkedList<? extends IEntity> entities) {
        super(name);
        this.entities = entities;
    }

    public String toString() {
        return "GetEntitiesByNamesResponse" + entities;
    }

}
