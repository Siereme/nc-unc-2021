package view.edit;

import controller.FilmController;
import view.IView;
import view.View;

public class EditFilmView extends View implements IView {
    private FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Film To Edit------");
            System.out.println(filmController.getFilms().toString());
            System.out.println("0. Exit");
            int option = getOption();
            if (option == 0) {
                break;
            } else {
                if (option < filmController.getFilms().size()) {
                    System.out.println(filmController.getFilms().get(option));
                }
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
