package view.edit.director;

import controller.DirectorController;
import model.Director.Director;
import view.IView;
import view.View;

import java.util.LinkedList;

public class EditDirectorMenuView extends View implements IView {
    private final String name = "Edit Director";
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        // флаг того, что данные изменились
        boolean isChange = false;
        while (show) {
            System.out.println("------Select Director To Edit------");
            System.out.println(directorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < directorController.size()) {
                    int directorInd = option;
                    Director director = directorController.getDirector(directorInd);
                    System.out.println(directorController.directorToString(director));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(directorInd);
                            isChange = true;
                            break;
                        case 2:
                            setFilms(directorInd);
                            isChange = true;
                            break;
                        case 3:
                            show = false;
                        default:
                            break;
                    }
                }
            }
        }
        if (isChange) {
            directorController.updateRepository();
        }
    }

    public String getName(){
        return this.name;
    }

    private void setName(int directorInd) {
        Director director = directorController.getDirector(directorInd);
        String newName = getStr("Enter a new Director name:\n");
        director.setName(newName);
    }

    private void setFilms(int directorInd) {
        Director director = directorController.getDirector(directorInd);
        LinkedList<String> newFilmsId = getFilmsId();
        director.setFilms(newFilmsId);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
