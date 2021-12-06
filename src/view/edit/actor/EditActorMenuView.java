package view.edit.actor;

import controller.ActorController;
import view.IView;
import view.View;

public class EditActorMenuView extends View implements IView {
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Actor To Edit------");
            System.out.println(actorController.getActorRepository());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < actorController.getActorRepository().findAll().size()) {
                    int actorInd = option;
                    System.out.println(actorController.getActorRepository().findAll().get(option));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(actorInd);
                            break;
                        case 2:
                            setFilms(actorInd);
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

    public void setName(int actorInd) {
        actorController.getActorRepository().findAll().get(actorInd).setName(getStr("Enter new name\n"));
    }

    public void setFilms(int actorInd) {
        actorController.setFilms(actorInd, getFilmsId());
    }

    @Override
    public void showMessage(String messsage) {

    }
}
