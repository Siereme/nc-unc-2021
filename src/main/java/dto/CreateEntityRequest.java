package dto;

import app.model.IEntity;

public class CreateEntityRequest<T extends IEntity> extends Request {
    public CreateEntityRequest( T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "CreateEntityRequest: request " + entity.toString();
    }

    T entity;
}
