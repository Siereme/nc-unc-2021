package view.show;

import controller.ActorController;
import view.IView;
import view.View;

public class ShowAllActorsView extends View implements IView {
    /** Поле название команды */
    public String name = "Show All Actors";

    ActorController actorController = new ActorController();

    @Override
    public void display() {
        System.out.println(actorController);
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
