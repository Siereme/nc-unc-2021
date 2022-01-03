package dto.request.imp;

import app.model.IEntity;
import dto.request.Request;

public class GetAllEntitiesRequest extends Request {

    public GetAllEntitiesRequest(Class<? extends IEntity> entityType) {
        this.entityType = entityType;
    }

    public String toString() {
        return "GetAllEntitiesRequest: request " + entityType.toString();
    }
}
