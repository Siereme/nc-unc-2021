package app.view.show;

import app.controller.DirectorController;
import app.model.user.IUser;
import app.view.View;

public class ShowAllDirectorsView extends View {
    /** Name of the command*/
    public final String name = "Show All Directors";

    final DirectorController directorController = new DirectorController();

    public ShowAllDirectorsView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(directorController.getAllEntitiesAsString());
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
