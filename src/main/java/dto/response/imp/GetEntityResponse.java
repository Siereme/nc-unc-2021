package dto.response.imp;

import app.model.IEntity;
import dto.response.Response;

/** request send entity to client*/
public class GetEntityResponse extends Response {

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, IEntity entity) {
        super(name);
        this.entity = entity;
    }

    private IEntity entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
