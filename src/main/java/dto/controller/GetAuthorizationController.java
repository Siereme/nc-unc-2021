package dto.controller;

import app.controller.IEntityController;
import app.controller.UserController;
import app.model.IEntity;
import app.model.user.IUser;

public class GetAuthorizationController {
    UserController userController = new UserController();

    public IUser getUser(String name, String password) {
        return userController.getEntityByLogin(name, password);
    }
}
