package dto.request.imp;

import app.model.IEntity;
import dto.request.Request;

public class EditEntityRequest extends Request {
    public EditEntityRequest(IEntity entity, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.entity = entity;
    }

    private final IEntity entity;

    public IEntity getEntity() {
        return entity;
    }
}
