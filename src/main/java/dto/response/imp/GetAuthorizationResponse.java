package dto.response.imp;

import app.model.user.IUser;
import dto.response.Response;

/** request send new User to client */
public class GetAuthorizationResponse extends Response {
    public GetAuthorizationResponse(String name, IUser newUser) {
        super(name);
        user = newUser;
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
