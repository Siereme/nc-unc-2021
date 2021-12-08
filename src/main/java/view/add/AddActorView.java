package view.add;

import controller.ActorController;
import model.Actor.Actor;
import view.IView;
import view.View;

import java.util.LinkedList;

public class AddActorView extends View implements IView {
    public String name = "Add Actor";
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        // флаг того, что данные изменились
        boolean isChange = false;
        while (show) {
            System.out.println("------Adding actor menu------");
            System.out.println("1. Add actor");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера(все поля пустые), а потом его редактируем
                actorController.addActor();
                int actorInd = actorController.size() - 1;
                setName(actorInd);
                setYear(actorInd);
                setFilms(actorInd);
                isChange = true;
            }
        }
        if (isChange) {
            actorController.updateRepository();
        }
    }

    public String getName(){
        return this.name;
    }

    public void setFilms(int actorInd) {
        Actor actor = actorController.getActor(actorInd);
        LinkedList<String> newFilmsId = getFilmsId();
        actor.setFilms(newFilmsId);
    }

    public void setYear(int actorInd) {
        Actor actor = actorController.getActor(actorInd);
        String newAge = getStr("Enter age\n");
        actor.setYear(newAge);
    }

    public void setName(int actorInd) {
        Actor actor = actorController.getActor(actorInd);
        String newName = getStr("Enter name\n");
        actor.setName(newName);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
