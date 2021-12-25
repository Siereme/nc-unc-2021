package app.view.add;

import app.controller.imp.ActorController;
import app.controller.imp.FilmController;
import app.model.actor.Actor;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

import java.util.LinkedList;

/** Class Command adding actor to app.repository */
public class AddActorView extends View implements IView {

    /** Name of the command*/
    public final String name = "Add Actor";

    final ActorController actorController = new ActorController();

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
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setFilms(Actor actor) {
        FilmController filmController = new FilmController();
        LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select films to add\n");
        // adding actor to new films
        filmController.addActorToFilms(actor, newFilmsId);
    }

    public void setYear(Actor actor) {
        String newAge = getStr("Enter age\n");
        actor.setYear(newAge);
    }

    public void setName(Actor actor) {
        String newName = getStr("Enter name\n");
        actor.setName(newName);
    }

}
