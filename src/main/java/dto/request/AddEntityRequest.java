package dto.request;

import app.model.IEntity;

public class AddEntityRequest extends Request{
    public IEntity getEntity() {
        return entity;
    }

    private final IEntity entity;

    public AddEntityRequest(IEntity entity) {
        this.entityType = entity.getClass();
        this.entity = entity;
    }
}
