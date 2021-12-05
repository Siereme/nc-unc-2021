package view.edit;

import view.IView;
import view.View;
import view.edit.actor.EditActorMenuView;
import view.edit.director.EditDirectorMenuView;
import view.edit.film.EditFilmMenuView;
import view.edit.genre.EditGenreMenuView;

public class EditView extends View implements IView {
    private final EditFilmMenuView editFilmMenuView = new EditFilmMenuView();
    private final EditActorMenuView editActorView = new EditActorMenuView();
    private final EditGenreMenuView editGenreView = new EditGenreMenuView();
    private final EditDirectorMenuView editDirectorView = new EditDirectorMenuView();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Edit Menu------");
            System.out.println("1. Edit Film");
            System.out.println("2. Edit Genre");
            System.out.println("3. Edit Actor");
            System.out.println("4. Edit Director");
            System.out.println("5. Exit");
            int option = getOption();
            switch (option) {
                case 1:
                    // готово
                    editFilmMenuView.display();
                    break;
                case 2:
                    // готово
                    editGenreView.display();
                    break;
                case 3:
                    editActorView.display();
                    break;
                case 4:
                    editDirectorView.display();
                    break;
                case 5:
                    show = false;
                default:
                    break;
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
