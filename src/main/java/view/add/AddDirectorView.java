package view.add;

import controller.DirectorController;
import model.Director.Director;
import view.IView;
import view.View;

public class AddDirectorView extends View implements IView {
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
                directorController.getDirectorRepository().findAll().add(new Director("no name"));
                int ind = directorController.getDirectorRepository().findAll().size() - 1;
                setName(ind);
                setYear(ind);
                setFilms(ind);
            }
        }
    }

    void setName(int dirInd) {
        directorController.getDirectorRepository().findAll().get(dirInd).setName(getStr("Enter Director Name\n"));
    }

    void setYear(int dirInd) {
        directorController.getDirectorRepository().findAll().get(dirInd).setYear(getStr("Enter Director Age"));
    }

    void setFilms(int dirInd) {
        directorController.getDirectorRepository().findAll().get(dirInd).setFilms(getFilmsId());
    }

    @Override
    public void showMessage(String messsage) {

    }
}
