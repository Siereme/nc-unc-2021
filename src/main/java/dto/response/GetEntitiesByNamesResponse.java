package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.controller.EntityController;
import dto.request.GetEntitiesByNamesRequest;

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
