package dto;

import model.IEntity;

public class GetEntityResponse<T extends IEntity> extends Response {
    public GetEntityResponse(String name) {
        super(name);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, T entity) {
        super(name);
        this.entity = entity;
    }

    T entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
