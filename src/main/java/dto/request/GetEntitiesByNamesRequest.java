package dto.request;

import app.controller.IEntityController;
import app.model.IEntity;

import java.util.LinkedList;

public class GetEntitiesByNamesRequest extends Request {
    public LinkedList<String> getNames() {
        return names;
    }

    private final LinkedList<String> names;

    public GetEntitiesByNamesRequest(LinkedList<String> names, Class<? extends IEntity> entityType) {
        this.entityType = entityType;
        this.names = names;
    }

    public String toString() {
        return "GetEntitiesByNamesRequest : request " + "names = " + names;
    }

}
