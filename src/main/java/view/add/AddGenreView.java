package view.add;

import controller.GenreController;
import model.genre.Genre;
import model.user.IUser;
import view.IView;
import view.View;

/** Command adding genre
 * @author Vasiliy
 * @version 1.0
 * */
public class AddGenreView extends View implements IView {
    /** Name of the command*/
    public String name = "Add Genre";

    private GenreController genreController = new GenreController();

    public AddGenreView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Genre menu------");
            System.out.println("1. Add Genre");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                Genre genre = new Genre();
                setTittle(genre);
                genreController.addEntity(genre);
                genreController.updateRepository();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    void setTittle(Genre genre) {
        String newTittle = getStr("Enter a tittle\n");
        genre.setTittle(newTittle);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
