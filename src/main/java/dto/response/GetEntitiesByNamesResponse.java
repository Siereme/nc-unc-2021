package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.controller.ClientServerFilmController;
import dto.controller.GetEntityController;
import dto.request.CreateGetEntitiesByNamesRequest;
import dto.request.CreateGetEntityRequest;

import java.util.LinkedList;

public class GetEntitiesByNamesResponse<T extends IEntity> extends Response {
    private final LinkedList<T> entities;

    public GetEntitiesByNamesResponse(String name, CreateGetEntitiesByNamesRequest<T> createGetEntitiesByNamesRequest) {
        super(name);
        IEntityController<T> entityController = createGetEntitiesByNamesRequest.getEntityController();
        GetEntityController<T> getEntityController = new GetEntityController<>(entityController);
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
