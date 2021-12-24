package dto.response;

import app.controller.IEntityController;
import app.model.IEntity;
import dto.request.RemoveEntityRequest;

public class GetRemoveEntityResponse<T extends IEntity> extends Response {

    public GetRemoveEntityResponse(String name, RemoveEntityRequest<T> removeEntityRequest) {
        super(name);
        IEntityController<T> entityController = removeEntityRequest.getEntityController();
        String removeEntityId = removeEntityRequest.getEntityId();
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
