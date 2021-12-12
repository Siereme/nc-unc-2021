package view.add;

import controller.ActorController;
import controller.DirectorController;
import controller.FilmController;
import model.film.Film;
import view.IView;
import view.View;

import java.util.Date;
import java.util.LinkedList;

/** Команда добавления фильма
 * @author Vasiliy
 * @version 1.0
 * */
public class AddFilmView extends View implements IView {
    /** Поле название команды */
    public String name = "Add Film";

    /** Поле контроллер для фильмов */
    FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Genre menu------");
            System.out.println("1. Add Genre");
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
        actorController.addFilmToActors(film, newActorsId);
        actorController.updateRepository();
    }

    public void setDirectors(Film film) {
        DirectorController directorController = new DirectorController();
        LinkedList<String> newDirectorsId = getEntitiesId(directorController, "Select Directors to add\n");
        film.setDirectors(newDirectorsId);
        directorController.addFilmToDirectors(film, newDirectorsId);
        directorController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
