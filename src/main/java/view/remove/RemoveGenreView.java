package view.remove;

import controller.GenreController;
import model.user.IUser;
import view.IView;
import view.View;

/** Command deletion genre
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveGenreView extends View implements IView {

    final GenreController genreController = new GenreController();

    public RemoveGenreView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(genreController);
    }

    public String getName(){
        return "Delete Genre";
    }

    @Override
    public void showMessage(String messsage) {

    }
}
