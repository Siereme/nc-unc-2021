package view.show;

import controller.GenreController;
import view.View;

public class ShowAllGenresView extends View {
    /** Поле название команды */
    public String name = "Show All Genres";

    GenreController genreController = new GenreController();

    @Override
    public void display() {
        System.out.println(genreController);
        pressAnyKeyToContinue();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
