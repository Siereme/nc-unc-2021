package view.show;

import controller.FilmController;
import view.IView;
import view.View;

public class ShowAllFilmsView extends View implements IView {
    FilmController filmController = new FilmController();

    @Override
    public void display() {
        System.out.println(filmController);
        pressAnyKeyToContinue();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
