package view.remove;

import controller.ActorController;
import model.user.IUser;
import view.IView;
import view.View;

/** Command delition actor
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveActorView extends View implements IView {
    private final String name = "Delete Actor";

    ActorController actorController = new ActorController();

    public RemoveActorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(actorController);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
