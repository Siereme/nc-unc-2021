package app.view.remove;

import app.controller.FilmController;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Command deletion film
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveFilmView extends View implements IView {

    final FilmController filmController = new FilmController();

    public RemoveFilmView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(filmController);
    }

    public String getName(){
        return "Delete Film";
    }


    @Override
    public void showMessage(String messsage) {

    }
}
