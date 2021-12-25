package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.controller.EntityController;
import dto.request.GetEntityRequest;

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
