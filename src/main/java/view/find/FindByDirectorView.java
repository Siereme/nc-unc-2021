package view.find;

import controller.DirectorController;
import controller.FilmController;
import model.director.Director;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Command searching films by director
 * @author Vasiliy
 * @version 1.0
 * */
public class FindByDirectorView extends View implements IView {
    /** Name of the command*/
    public String name = "Find By Director";

    DirectorController directorController = new DirectorController();

    public FindByDirectorView(IUser currentUser) {
        super(currentUser);
    }

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
