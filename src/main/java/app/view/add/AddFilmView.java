package app.view.add;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.FilmController;
import app.model.film.Film;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

import java.util.Date;
import java.util.LinkedList;

/** Command adding film
 * @author Vasiliy
 * @version 1.0
 * */
public class AddFilmView extends View implements IView {
    /** Name of the command*/
    public final String name = "Add Film";

    final FilmController filmController = new FilmController();

    public AddFilmView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Genre menu------");
            System.out.println("1. Add Film");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                Film film = new Film();
                setTittle(film);
                setDate(film);
                setActors(film);
                setDirectors(film);
                filmController.addEntity(film);
                filmController.updateRepository();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setTittle(Film film) {
        String newTittle = getStr("Enter a tittle\n");
        film.setTittle(newTittle);
    }

    public void setDate(Film film) {
        Date newDate = getDate();
        film.setDate(newDate);
    }

    public void setActors(Film film) {
        ActorController actorController = new ActorController();
        LinkedList<String> newActorsId = getEntitiesId(actorController, "Select Actors to add\n");
        film.setActors(newActorsId);
    }

    public void setDirectors(Film film) {
        DirectorController directorController = new DirectorController();
        LinkedList<String> newDirectorsId = getEntitiesId(directorController, "Select Directors to add\n");
        film.setDirectors(newDirectorsId);
    }

}
