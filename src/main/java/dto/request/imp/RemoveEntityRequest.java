package dto.request.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.request.Request;

public class RemoveEntityRequest extends Request {

    public String getEntityId() {
        return entityId;
    }

    private final String entityId;

    public RemoveEntityRequest(String entityId, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.entityId = entityId;
    }

    public String toString() {
        return "CreateRemoveEntityRequest: request " + entityId;
    }

}
