package dto.request.imp;

import app.model.IEntity;
import dto.request.Request;

import java.util.LinkedList;

public class GetEntitiesByNamesRequest extends Request {
    public LinkedList<String> getNames() {
        return names;
    }

    private final LinkedList<String> names;

    public GetEntitiesByNamesRequest(LinkedList<String> names, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.names = names;
    }

    public String toString() {
        return "GetEntitiesByNamesRequest : request " + "names = " + names;
    }

}
