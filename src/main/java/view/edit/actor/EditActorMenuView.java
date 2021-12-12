package view.edit.actor;

import controller.ActorController;
import controller.FilmController;
import model.actor.Actor;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Класс команда изменения актера
 * @author Vasiliy
 * @version 1.0
 * */
public class EditActorMenuView extends View implements IView {
    /** Поле название команды */
    private final String name = "Edit Actor";

    /** Поле контроллер для актеров */
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
                    Actor actor = actorController.getEntity(actorInd);
                    System.out.println(actorController.entityToString(actor));
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

    public String getName() {
        return this.name;
    }

    public void setName(int actorInd) {
        Actor actor = actorController.getEntity(actorInd);
        final String newName = getStr("Enter a new name\n");
        actor.setName(newName);
    }

    public void setFilms(int actorInd) {
        Actor actor = actorController.getEntity(actorInd);
        FilmController filmController = new FilmController();
        final LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select Films\n");
        actor.setFilms(newFilmsId);
        filmController.removeActorFromAllFilms(actor);
        filmController.addActorToFilms(actor, newFilmsId);
        filmController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
