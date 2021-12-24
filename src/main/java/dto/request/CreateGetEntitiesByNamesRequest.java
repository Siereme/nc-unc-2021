package dto.request;

import app.controller.IEntityController;
import app.model.IEntity;

import java.util.LinkedList;

public class CreateGetEntitiesByNamesRequest<T extends IEntity> extends Request {
    public LinkedList<String> getNames() {
        return names;
    }

    private final LinkedList<String> names;

    public IEntityController<T> getEntityController() {
        return entityController;
    }

    private final IEntityController<T> entityController;

    CreateGetEntitiesByNamesRequest(LinkedList<String> names, IEntityController<T> entityController) {
        this.names = names;
        this.entityController = entityController;
    }

    public String toString() {
        return "GetEntitiesByNamesRequest : request " + "names = " + names;
    }

}
