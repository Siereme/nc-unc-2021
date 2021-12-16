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
        boolean show = true;
        while (show) {
            System.out.println("------Remove Actor Menu------");
            System.out.println("Select Actor to Delete");
            System.out.println(actorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < actorController.size()) {
                if (getConfirm()) {
                    actorController.removeEntity(option);
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
