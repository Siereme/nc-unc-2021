package view.remove;

import controller.GenreController;
import view.IView;
import view.View;

public class RemoveGenreView extends View implements IView {
    private final String name = "Delete Genre";
    GenreController genreController = new GenreController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Genre Menu------");
            System.out.println("Select Genre to Delete");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < genreController.size()) {
                if (getConfirm()) {
                    genreController.removeEntity(option);
                    genreController.updateRepository();
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
