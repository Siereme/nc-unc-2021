package app.view.authorization.login;

import app.controller.imp.UserController;
import app.model.user.IUser;
import app.view.IView;

import java.util.Scanner;

/** Menu of authorization
 * @author Sergey
 * @version 1.0
 * */
public class LoginView implements IView {
    private IUser currentUser;
    private final Scanner input = new Scanner(System.in);

    @Override
    public void display() {
        boolean show = true;
        while (show){
            System.out.println("------Login------");

            System.out.println("Write login and password:");
            String login = getOption();
            String password = getOption();

            UserController controller = new UserController();
            currentUser = controller.getEntityByLogin(login, password);

            if(currentUser == null){
                System.out.println("Incorrect login or password");
                continue;
            }

            show = false;
        }
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    private String getOption(){
        return this.input.nextLine();
    }

}
