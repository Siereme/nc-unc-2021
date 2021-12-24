package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.controller.EntityController;
import dto.request.CreateGetEntitiesByNamesRequest;

import java.util.LinkedList;

public class GetEntitiesByNamesResponse<T extends IEntity> extends Response {
    private final LinkedList<T> entities;

    public GetEntitiesByNamesResponse(String name, CreateGetEntitiesByNamesRequest<T> createGetEntitiesByNamesRequest) {
        super(name);
        IEntityController<T> entityController = createGetEntitiesByNamesRequest.getEntityController();
        EntityController<T> getEntityController = new EntityController<>(entityController);
        LinkedList<String> names = createGetEntitiesByNamesRequest.getNames();
        entities = getEntityController.getEntitiesByNames(names);
    }

    public LinkedList<T> getEntities() {
        return entities;
    }

    public String toString() {
        return "GetEntitiesByNamesResponse" + entities;
    }

}
