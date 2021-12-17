package view.show;

import controller.ActorController;
import model.user.IUser;
import view.IView;
import view.View;

public class ShowAllActorsView extends View implements IView {
    /** Name of the command*/
    public final String name = "Show All Actors";

    final ActorController actorController = new ActorController();

    public ShowAllActorsView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(actorController.getAllEntitiesAsString());
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
