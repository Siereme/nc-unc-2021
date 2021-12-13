package view.edit.director;

import controller.DirectorController;
import controller.FilmController;
import model.director.Director;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Класс команда изменения режиссеров
 * @author Vasiliy
 * @version 1.0
 * */
public class EditDirectorMenuView extends View implements IView {
    /** Поле название команды */
    private final String name = "Edit Director";

    /** Поле контроллер для режиссеров */
    DirectorController directorController = new DirectorController();

    public EditDirectorMenuView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Director To Edit------");
            System.out.println(directorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < directorController.size()) {
                    int directorInd = option;
                    Director director = directorController.getEntity(directorInd);
                    System.out.println(directorController.entityToString(director));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(directorInd);
                            directorController.updateRepository();
                            break;
                        case 2:
                            setFilms(directorInd);
                            directorController.updateRepository();
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

    private void setName(int directorInd) {
        Director director = directorController.getEntity(directorInd);
        String newName = getStr("Enter a new Director name:\n");
        director.setName(newName);
    }

    private void setFilms(int directorInd) {
        Director director = directorController.getEntity(directorInd);
        FilmController filmController = new FilmController();
        LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select Films\n");
        director.setFilms(newFilmsId);
        filmController.removeDirectorFromAllFilms(director);
        filmController.addDirectorToFilms(director, newFilmsId);
        filmController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
