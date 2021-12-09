package view.add;

import controller.FilmController;
import model.Film.Film;
import view.IView;
import view.View;

import java.util.Date;
import java.util.LinkedList;

public class AddFilmView extends View implements IView {
    public String name = "Add Film";
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
                filmController.addEntity();
                int ind = filmController.size() - 1;
                setTittle(ind);
                setDate(ind);
                setActors(ind);
                setDirectors(ind);
                filmController.updateRepository();
            }
        }
    }

    public String getName(){
        return this.name;
    }

    public void setTittle(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        String newTittle = getStr("Enter a tittle\n");
        film.setTittle(newTittle);
    }

    public void setDate(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        Date newDate = getDate();
        film.setDate(newDate);
    }

    public void setActors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        LinkedList<String> newActorsId = getActorsId();
        film.setActors(newActorsId);
    }

    public void setDirectors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        LinkedList<String> newDirectorsId = getDirectorsId();
        film.setDirectors(newDirectorsId);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
