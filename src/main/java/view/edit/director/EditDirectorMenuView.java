package view.edit.director;

import controller.DirectorController;
import view.IView;
import view.View;

public class EditDirectorMenuView extends View implements IView {
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Director To Edit------");
            System.out.println(directorController.getDirectorRepository());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < directorController.getDirectorRepository().findAll().size()) {
                    int directorInd = option;
                    System.out.println(directorController.directorToString(
                            directorController.getDirectorRepository().findAll().get(directorInd)));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(directorInd);
                            break;
                        case 2:
                            setFilms(directorInd);
                            break;
                        case 3:
                            show = false;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void setName(int directorInd) {
        directorController.getDirectorRepository().findAll().get(directorInd)
                .setName(getStr("Enter a new Director name:\n"));
    }

    private void setFilms(int directorInd) {
        directorController.setFilms(directorInd, getFilmsId());
    }

    @Override
    public void showMessage(String messsage) {

    }
}
