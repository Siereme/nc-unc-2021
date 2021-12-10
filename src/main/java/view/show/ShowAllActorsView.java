package view.show;

import controller.ActorController;
import view.IView;
import view.View;

public class ShowAllActorsView extends View implements IView {
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        System.out.println(actorController);
        pressAnyKeyToContinue();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
