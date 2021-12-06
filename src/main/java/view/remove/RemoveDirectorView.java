package view.remove;

import controller.DirectorController;
import view.IView;
import view.View;

public class RemoveDirectorView extends View implements IView {
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Director menu------");
            System.out.println("Select director to remove");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option >= 0 && option < directorController.getDirectorRepository().findAll().size()) {
                // directorController.remove(option);
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
