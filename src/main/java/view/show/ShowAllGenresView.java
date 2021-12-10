package view.show;

import controller.GenreController;
import view.View;

public class ShowAllGenresView extends View {
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
