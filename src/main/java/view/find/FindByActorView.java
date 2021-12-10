package view.find;

import controller.ActorController;
import controller.FilmController;
import model.Actor.Actor;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Команда поиска фильмов по актерам
 * @author Vasiliy
 * @version 1.0
 * */
public class FindByActorView extends View implements IView {
    /** Поле название команды */
    public String name = "Find By Actor";

    /** Поле контроллер для актеров */
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Find By Actor Menu------");
            System.out.println("------Select Actor------");
            System.out.println(actorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option < actorController.size() && option >= 0) {
                Actor actor = actorController.getEntity(option);
                FilmController filmController = new FilmController();
                LinkedList<String> filmsId = filmController.getFilmsByActor(actor);
                System.out.println(filmController.entitiesByIDsToString(filmsId));
                pressAnyKeyToContinue();
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }

}
