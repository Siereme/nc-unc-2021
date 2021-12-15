package view.remove;

import controller.FilmController;
import model.user.IUser;
import view.IView;
import view.View;

/** Класс команда удаления фильма
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveFilmView extends View implements IView {
    /** Поле название команды */
    private final String name = "Delete Film";

    /** Поле контроллер для фильмов */
    FilmController filmController = new FilmController();

    public RemoveFilmView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Film Menu------");
            System.out.println("Select Film to Delete");
            System.out.println(filmController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < filmController.size()) {
                if (getConfirm()) {
                    filmController.removeEntity(option);
                }
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
