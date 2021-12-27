package dto.request.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.request.Request;

/** request send id to server */
public class GetEntityRequest extends Request {
    public String getEntityId() {
        return entityId;
    }

    private final String entityId;

    public GetEntityRequest(String id, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.entityType = entityType;
        entityId = id;
    }

    public String toString() {
        return "CreateGetEntityRequest: request " + entityId;
    }

}
