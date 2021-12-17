package view.show;

import controller.FilmController;
import model.user.IUser;
import view.IView;
import view.View;

public class ShowAllFilmsView extends View implements IView {
    /** Name of the command*/
    public final String name = "Show All Films";

    final FilmController filmController = new FilmController();

    public ShowAllFilmsView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        System.out.println(filmController.getAllEntitiesAsString());
        pressAnyKeyToContinue();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
