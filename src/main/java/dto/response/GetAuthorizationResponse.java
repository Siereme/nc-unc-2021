package dto.response;

import app.model.user.IUser;
import dto.controller.GetAuthorizationController;
import dto.request.AuthorizationRequest;

/** request send new User to client */
public class GetAuthorizationResponse extends Response {
    public GetAuthorizationResponse(String name, AuthorizationRequest authorizationRequest) {
        super(name);

        GetAuthorizationController authorizationController = new GetAuthorizationController();
        String userName = authorizationRequest.getUserName();
        String password = authorizationRequest.getPassword();
        user = authorizationController.getUser(userName, password);
    }

    private final IUser user;

    public IUser getUser() {
        return user;
    }

    public boolean isSuccessfully() {
        return user != null;
    }

    public String toString() {
        if (user != null) {
            return "GetAuthorizationResponse: response successfully";
        } else {
            return "GetAuthorizationResponse: response failed";
        }
    }

}
