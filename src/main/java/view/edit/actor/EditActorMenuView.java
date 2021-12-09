package view.edit.actor;

import controller.ActorController;
import model.Actor.Actor;
import view.IView;
import view.View;

import java.util.LinkedList;

public class EditActorMenuView extends View implements IView {
    private final String name = "Edit Actor";
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Actor To Edit------");
            System.out.println(actorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < actorController.size()) {
                    int actorInd = option;
                    Actor actor = actorController.getActor(actorInd);
                    System.out.println(actorController.actorToString(actor));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(actorInd);
                            actorController.updateRepository();
                            break;
                        case 2:
                            setFilms(actorInd);
                            actorController.updateRepository();
                            break;
                        case 3:
                            show = false;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }

    public void setName(int actorInd) {
        Actor actor = actorController.getActor(actorInd);
        final String newName = getStr("Enter a new name\n");
        actor.setName(newName);
    }

    public void setFilms(int actorInd) {
        Actor actor = actorController.getActor(actorInd);
        final LinkedList<String> newFilmsId = getFilmsId();
        actor.setFilms(newFilmsId);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
