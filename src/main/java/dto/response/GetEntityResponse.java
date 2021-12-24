package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.controller.EntityController;
import dto.request.GetEntityRequest;

/** request send entity to client*/
public class GetEntityResponse<T extends IEntity> extends Response {

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, GetEntityRequest<T> entityRequest) {
        super(name);
        IEntityController<T> entityController = entityRequest.getEntityController();
        EntityController<T> getEntityController = new EntityController<>(entityController);
        String entityId = entityRequest.getEntityId();
        entity = getEntityController.getEntity(entityId);
    }

    private T entity;

    @Override
    public String toString() {
        return "GetEntityResponse: response " + entity.toString();
    }

}
