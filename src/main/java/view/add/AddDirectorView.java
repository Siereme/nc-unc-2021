package view.add;

import controller.DirectorController;
import controller.FilmController;
import model.director.Director;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Command adding director
 * @author Vasiliy
 * @version 1.0
 * */
public class AddDirectorView extends View implements IView {
    /** Name of the command*/
    public final String name = "Add Director";

    final DirectorController directorController = new DirectorController();

    public AddDirectorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Director menu------");
            System.out.println("1. Add director");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                Director director = new Director();
                setName(director);
                setYear(director);
                setFilms(director);
                directorController.addEntity(director);
                directorController.updateRepository();
            }
        }

    }

    public String getName() {
        return this.name;
    }

    void setName(Director director) {
        String newName = getStr("Enter Director Name\n");
        director.setName(newName);
    }

    void setYear(Director director) {
        String newAge = getStr("Enter Director Age\n");
        director.setYear(newAge);
    }

    void setFilms(Director director) {
        FilmController filmController = new FilmController();
        LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select film to add\n");
        filmController.addDirectorToFilms(director, newFilmsId);
        filmController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
