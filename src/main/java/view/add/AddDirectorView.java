package view.add;

import controller.DirectorController;
import model.Director.Director;
import view.IView;
import view.View;

import java.util.LinkedList;

public class AddDirectorView extends View implements IView {
    public String name = "Add Director";
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Director menu------");
            System.out.println("1. Add director");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                directorController.addEntity();
                int ind = directorController.size() - 1;
                setName(ind);
                setYear(ind);
                setFilms(ind);
                directorController.updateRepository();
            }
        }

    }

    public String getName() {
        return this.name;
    }

    void setName(int dirInd) {
        Director director = directorController.getEntity(dirInd);
        String newName = getStr("Enter Director Name\n");
        director.setName(newName);
    }

    void setYear(int dirInd) {
        Director director = directorController.getEntity(dirInd);
        String newAge = getStr("Enter Director Age\n");
        director.setYear(newAge);
    }

    void setFilms(int dirInd) {
        Director director = directorController.getEntity(dirInd);
        LinkedList<String> newFilmsId = getFilmsId();
        director.setFilms(newFilmsId);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
