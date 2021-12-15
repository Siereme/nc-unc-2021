package view.show;

import controller.GenreController;
import model.user.IUser;
import view.View;

public class ShowAllGenresView extends View {
    /** Поле название команды */
    public String name = "Show All Genres";

    GenreController genreController = new GenreController();

    public ShowAllGenresView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(genreController);
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
