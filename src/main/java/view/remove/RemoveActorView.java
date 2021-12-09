package view.remove;

import controller.ActorController;
import view.IView;
import view.View;

public class RemoveActorView extends View implements IView {
    private final String name = "Delete Actor";
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Actor Menu------");
            System.out.println("Select Actor to Delete");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < actorController.size()) {
                if (getConfirm()) {
                    actorController.removeEntity(option);
                    actorController.updateRepository();
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
