package view.show;

import controller.DirectorController;
import model.user.IUser;
import view.View;

public class ShowAllDirectorsView extends View {
    /** Поле название команды */
    public String name = "Show All Directors";

    DirectorController directorController = new DirectorController();

    public ShowAllDirectorsView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(directorController);
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
