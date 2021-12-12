package view.find;

import controller.DirectorController;
import controller.FilmController;
import model.director.Director;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Команда поиска фильмов по режиссерам
 * @author Vasiliy
 * @version 1.0
 * */
public class FindByDirectorView extends View implements IView {
    /** Поле название команды */
    public String name = "Find By Director";

    /** Поле контроллер для режиссеров */
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Find By Director Menu------");
            System.out.println("------Select Director------");
            System.out.println(directorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option < directorController.size() && option >= 0) {
                Director director = directorController.getEntity(option);
                FilmController filmController = new FilmController();
                LinkedList<String> filmsId = filmController.getFilmsByDirector(director);
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
