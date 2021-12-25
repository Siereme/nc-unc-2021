package dto.request;

import app.model.user.IUser;

/** request send user name and password to server */
public class AuthorizationRequest extends Request {


    public AuthorizationRequest(String userName, String password) {
        setEntityType(IUser.class);
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    private final String userName;

    public String getPassword() {
        return password;
    }

    private final String password;

    public String toString(){
        return "CreateAuthorizationRequest: request " + "user name: " + userName + " password: " + password;
    }

}
