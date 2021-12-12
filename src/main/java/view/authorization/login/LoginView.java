package view.authorization.login;

import controller.UserController;
import model.User.IUser;
import view.IView;
import view.MainMenuView;

import java.util.Scanner;

public class LoginView implements IView {
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
            IUser user = controller.getEntityByLogin(login, password);

            if(user == null){
                System.out.println("Incorrect login or password");
                continue;
            }

            MainMenuView mainMenu = new MainMenuView(user);
            mainMenu.display();

            show = false;
        }
    }

    private String getOption(){
        return this.input.nextLine();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
