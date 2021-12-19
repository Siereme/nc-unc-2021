package dto;

import controller.UserController;
import model.user.IUser;

public class GetAuthorizationResponse extends Response {
    public GetAuthorizationResponse(String name) {
        super(name);
    }

    public GetAuthorizationResponse(String name, String userName, String password) {
        super(name);
        // check authorization
        UserController userController = new UserController();
        IUser currentUser = userController.getEntityByLogin(userName, password);
        if (currentUser != null) {
            user = currentUser;
        }
    }

    private IUser user;

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
