package dto.request;

import app.controller.IEntityController;
import app.model.IEntity;

public class RemoveEntityRequest extends Request {

    public IEntity getEntity() {
        return entity;
    }

    private final IEntity entity;

    public RemoveEntityRequest(IEntity entity, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.entityType = entityType;
        this.entity = entity;
    }

    public String toString() {
        return "CreateRemoveEntityRequest: request " + entity;
    }

}
