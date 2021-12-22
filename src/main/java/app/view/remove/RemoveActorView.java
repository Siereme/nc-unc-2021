package app.view.remove;

import app.controller.ActorController;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Command delition actor
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveActorView extends View implements IView {

    final ActorController actorController = new ActorController();

    public RemoveActorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(actorController);
    }

    public String getName() {
        return "Delete Actor";
    }

}
