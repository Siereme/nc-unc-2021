package dto.request;

/** request send user name and password to server */
public class CreateAuthorizationRequest extends Request {


    public CreateAuthorizationRequest(String name, String userName, String password) {
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
