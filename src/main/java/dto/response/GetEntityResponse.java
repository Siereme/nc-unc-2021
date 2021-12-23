package dto.response;

import app.model.IEntity;
import dto.controller.GetEntityController;
import dto.request.CreateGetEntityRequest;

/** request send entity to client*/
public class GetEntityResponse extends Response {

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, CreateGetEntityRequest entityRequest) {
        super(name);
        GetEntityController<IEntity> entityController = new GetEntityController<IEntity>();
        String entityId = entityRequest.getEntityId();
        entity = entityController.getEntity(entityId);
    }

    IEntity entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
