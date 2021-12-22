package app.view.show;

import app.controller.ActorController;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

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

}
