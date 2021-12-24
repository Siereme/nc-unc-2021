package dto.response;

import app.model.IEntity;
import dto.controller.GetEntityController;
import dto.request.CreateGetEntityRequest;

/** request send entity to client*/
public class GetEntityResponse<T extends IEntity> extends Response {

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, CreateGetEntityRequest entityRequest) {
        super(name);
        GetEntityController<T> entityController = new GetEntityController<>();
        String entityId = entityRequest.getEntityId();
        entity = entityController.getEntity(entityId);
    }

    private T entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
