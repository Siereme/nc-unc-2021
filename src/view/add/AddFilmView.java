package view.add;

import controller.FilmController;
import model.Film.Film;
import view.IView;
import view.View;

public class AddFilmView extends View implements IView {
    FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Genre menu------");
            System.out.println("1. Add Genre");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                filmController.getFilmsRepository().findAll().add(new Film("no name"));
                int ind = filmController.getFilmsRepository().findAll().size() - 1;
                setTittle(ind);
                setDate(ind);
                setActors(ind);
                setDirectors(ind);
            }
        }
    }

    public void setTittle(int filmInd) {
        filmController.getFilmsRepository().findAll().get(filmInd).setTittle(getStr("Enter a tittle\n"));
    }

    public void setDate(int filmInd) {
        filmController.getFilmsRepository().findAll().get(filmInd).setDate(getDate());
    }

    public void setActors(int filmInd) {

    }

    public void setDirectors(int filmInd) {

    }

    @Override
    public void showMessage(String messsage) {

    }
}
