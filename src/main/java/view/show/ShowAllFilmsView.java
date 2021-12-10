package view.show;

import controller.FilmController;
import view.IView;
import view.View;

public class ShowAllFilmsView extends View implements IView {
    /** Поле название команды */
    public String name = "Show All Films";

    FilmController filmController = new FilmController();

    @Override
    public void display() {
        System.out.println(filmController);
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
