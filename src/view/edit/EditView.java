package view.edit;

import view.IView;
import view.View;

public class EditView extends View implements IView {
    private final EditFilmView editFilmView = new EditFilmView();
    private final EditActorView editActorView = new EditActorView();
    private final EditGenreView editGenreView = new EditGenreView();
    private final EditDirectorView editDirectorView = new EditDirectorView();

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
                    editFilmView.display();
                    break;
                case 2:
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
