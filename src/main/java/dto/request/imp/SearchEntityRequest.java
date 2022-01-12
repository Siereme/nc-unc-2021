package dto.request.imp;

import app.model.IEntity;
import dto.request.Request;

public class SearchEntityRequest extends Request {
    public String getEntityName() {
        return entityName;
    }

    private final String entityName;

    public SearchEntityRequest(String entityName, Class<? extends IEntity> entityType) {
        this.entityName = entityName;
        setEntityType(entityType);
    }

    public String toString() {
        return "SearchEntityRequest " + entityType.getName();
    }

}
