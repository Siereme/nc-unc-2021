package dto.request.imp;

import app.model.IEntity;
import dto.request.Request;

import java.util.LinkedList;
import java.util.List;

public class GetEntitiesByNamesRequest extends Request {
    public List<String> getNames() {
        return names;
    }

    private final List<String> names;

    public GetEntitiesByNamesRequest(List<String> names, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.names = names;
    }

    public String toString() {
        return "GetEntitiesByNamesRequest : request " + "names = " + names;
    }

}
