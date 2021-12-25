package dto.controller;

import app.controller.imp.UserController;
import app.model.user.IUser;

public class GetAuthorizationController {
    UserController userController = new UserController();

    public IUser getUser(String name, String password) {
        return userController.getEntityByLogin(name, password);
    }
}
