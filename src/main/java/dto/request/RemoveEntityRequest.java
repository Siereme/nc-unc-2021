package dto.request;

import app.controller.IEntityController;
import app.model.IEntity;

public class RemoveEntityRequest<T extends IEntity> extends Request{
    public String getEntityId() {
        return entityId;
    }

    private final String entityId;



    public RemoveEntityRequest(String id, Class<? extends IEntity> entityType) {
        this.entityType = entityType;
        entityId = id;
    }

    public String toString() {
        return "CreateRemoveEntityRequest: request " + entityId;
    }


}
