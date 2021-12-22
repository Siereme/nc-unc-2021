package dto;

public class CreateAuthorizationRequest extends Request {


    public CreateAuthorizationRequest(String name, String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    private String userName;

    public String getPassword() {
        return password;
    }

    private String password;

    public String toString(){
        return "CreateAuthorizationRequest: request " + "user name: " + userName + " password: " + password;
    }

}
