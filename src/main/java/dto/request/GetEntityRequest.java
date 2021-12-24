package dto.request;

import app.controller.IEntityController;
import app.model.IEntity;

/** request send id to server */
public class GetEntityRequest<T extends IEntity> extends Request {
    public String getEntityId() {
        return entityId;
    }

    private final String entityId;

    public IEntityController<T> getEntityController() {
        return entityController;
    }

    private final IEntityController<T> entityController;

    public GetEntityRequest(String id, IEntityController<T> entityController) {
        entityId = id;
        this.entityController = entityController;
    }

    public String toString() {
        return "CreateGetEntityRequest: request " + entityId;
    }

}
