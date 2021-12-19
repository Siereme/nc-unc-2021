package dto;

import model.IEntity;

public class CreateEntityRequest<T extends IEntity> extends Request {
    public CreateEntityRequest(String name) {
        super(name);
    }

    public CreateEntityRequest(String name, T entity) {
        super(name);
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "CreateEntityRequest: request " + entity.toString();
    }

    T entity;
}
