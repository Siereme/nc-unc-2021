package view.add;

import controller.ActorController;
import controller.FilmController;
import model.actor.Actor;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Команда добавления актера */
public class AddActorView extends View implements IView {

    /** Название команды*/
    public String name = "Add Actor";

    /** Контроллер для актеров */
    ActorController actorController = new ActorController();

    public AddActorView(IUser currentUser) {
        super(currentUser);
    }

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
                Actor actor = new Actor();
                setName(actor);
                setYear(actor);
                setFilms(actor);
                actorController.addEntity(actor);
                actorController.updateRepository();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setFilms(Actor actor) {
        FilmController filmController = new FilmController();
        LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select films to add\n");
        // adding new films to actor
        actor.setFilms(newFilmsId);
        // adding actor to new films
        filmController.addActorToFilms(actor, newFilmsId);
        filmController.updateRepository();
    }

    public void setYear(Actor actor) {
        String newAge = getStr("Enter age\n");
        actor.setYear(newAge);
    }

    public void setName(Actor actor) {
        String newName = getStr("Enter name\n");
        actor.setName(newName);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
