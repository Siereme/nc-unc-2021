package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.request.CreateRemoveEntityRequest;

public class GetRemoveEntityResponse<T extends IEntity> extends Response {

    public GetRemoveEntityResponse(String name, CreateRemoveEntityRequest<T> createRemoveEntityRequest) {
        super(name);
        IEntityController<T> entityController = createRemoveEntityRequest.getEntityController();
        String removeEntityId = createRemoveEntityRequest.getEntityId();
        isSuccessfully = entityController.remove(removeEntityId);
    }

    public boolean isSuccessfully() {
        return isSuccessfully;
    }

    private final boolean isSuccessfully;

    public String toString() {
        String result;
        if (isSuccessfully) {
            result = "ok";
        } else {
            result = "not ok";
        }
        return "GetRemoveEntityResponse: " + result;
    }

}
