package view.show;

import controller.GenreController;
import model.user.IUser;
import view.View;

public class ShowAllGenresView extends View {
    /** Name of the command*/
    public final String name = "Show All Genres";

    final GenreController genreController = new GenreController();

    public ShowAllGenresView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(genreController.getAllEntitiesAsString());
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
