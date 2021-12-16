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
    private final String name = "Delete Genre";

    GenreController genreController = new GenreController();

    public RemoveGenreView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Genre Menu------");
            System.out.println("Select Genre to Delete");
            System.out.println(genreController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < genreController.size()) {
                if (getConfirm()) {
                    genreController.removeEntity(option);
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
