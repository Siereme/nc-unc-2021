package dto.response.imp;

import app.model.IEntity;
import dto.response.Response;

/** request send entity to client*/
public class  GetEntityResponse <T extends  IEntity> extends Response {

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

    private T entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
