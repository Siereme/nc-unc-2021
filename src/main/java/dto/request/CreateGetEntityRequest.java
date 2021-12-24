package dto.request;

/** request send id to server */
public class CreateGetEntityRequest extends Request {
    public String getEntityId() {
        return entityId;
    }

    private final String entityId;

    public CreateGetEntityRequest(String id) {
        entityId = id;
    }

    public String toString() {
        return "CreateGetEntityRequest: request " + entityId;
    }

}
