package dto.response;

import app.model.IEntity;
import dto.controller.ClientServerFilmController;
import dto.controller.GetEntityController;
import dto.request.CreateGetEntitiesByNamesRequest;
import dto.request.CreateGetEntityRequest;

import java.util.LinkedList;

public class GetEntitiesByNamesResponse extends Response {
    private final LinkedList<IEntity> entities;

    public GetEntitiesByNamesResponse(String name, CreateGetEntitiesByNamesRequest createGetEntitiesByNamesRequest) {
        super(name);
        GetEntityController<IEntity> getEntityController = new GetEntityController<>();
        LinkedList<String> names = createGetEntitiesByNamesRequest.getNames();
        entities = getEntityController.getEntitiesByNames(names);
    }

    public LinkedList<IEntity> getEntities() {
        return entities;
    }

    public String toString() {
        return "GetEntitiesByNamesResponse" + entities;
    }

}
