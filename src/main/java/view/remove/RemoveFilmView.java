package view.remove;

import controller.FilmController;
import view.IView;
import view.View;

public class RemoveFilmView extends View implements IView {
    FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Film Menu------");
            System.out.println("Select Film to Delete");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option >= 0 && option < filmController.size()) {
                if (getConfirm()) {
                    filmController.remove(option);
                    filmController.updateRepository();
                }
            }
        }
    }


    @Override
    public void showMessage(String messsage) {

    }
}
