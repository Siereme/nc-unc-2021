package view.add;

import controller.ActorController;
import model.Actor.Actor;
import view.IView;
import view.View;

public class AddActorView extends View implements IView {
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding actor menu------");
            System.out.println("1. Add actor");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                actorController.getActorRepository().findAll().add(new Actor("no name"));
                int ind = actorController.getActorRepository().findAll().size() - 1;
                setName(ind);
                setYear(ind);
                setFilms(ind);
            }
        }
    }

    public void setFilms(int actorInd) {
        actorController.getActorRepository().findAll().get(actorInd).setFilms(getFilmsId());
    }

    public void setYear(int actorInd) {
        actorController.getActorRepository().findAll().get(actorInd).setYear(getStr("Enter year\n"));
    }

    public void setName(int actorInd) {
        actorController.getActorRepository().findAll().get(actorInd).setName(getStr("Enter name\n"));
    }

    @Override
    public void showMessage(String messsage) {

    }
}
