package dto.request;

import app.model.IEntity;

public class EditEntityRequest extends Request{
    public EditEntityRequest(IEntity entity) {
        this.entityType = entity.getClass();
        this.entity = entity;
    }

    private final IEntity entity;

    public IEntity getEntity() {
        return entity;
    }
}
