package view.edit.film;

import controller.FilmController;
import model.Film.Film;
import view.IView;
import view.View;

import java.util.Date;
import java.util.LinkedList;

public class EditFilmMenuView extends View implements IView {
    private final String name = "Edit Film";
    private final FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Film To Edit------");
            System.out.println(filmController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < filmController.size()) {
                    int filmInd = option;
                    boolean show1 = true;
                    while (show1) {
                        Film film = filmController.getEntity(filmInd);
                        System.out.println(filmController.entityToString(film));
                        System.out.println("1. Change Tittle");
                        System.out.println("2. Change date");
                        System.out.println("3. Change genres");
                        System.out.println("4. Change directors");
                        System.out.println("5. Change actors");
                        System.out.println("6. Exit");
                        option = getOption();
                        switch (option) {
                            case 1:
                                setTittle(filmInd);
                                filmController.updateRepository();
                                break;
                            case 2:
                                setDate(filmInd);
                                filmController.updateRepository();
                                break;
                            case 3:
                                setGenres(filmInd);
                                filmController.updateRepository();
                                break;
                            case 4:
                                setDirectors(filmInd);
                                filmController.updateRepository();
                                break;
                            case 5:
                                setActors(filmInd);
                                filmController.updateRepository();
                                break;
                            case 6:
                                show1 = false;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }

    private void setTittle(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        String newTittle = getStr("Enter a tittle\n");
        film.setTittle(newTittle);
    }

    private void setDate(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        Date newDate = getDate();
        film.setDate(newDate);
    }

    private void setGenres(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        LinkedList<String> newGenresId = getGenresId();
        film.setGenres(newGenresId);
    }

    private void setDirectors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        LinkedList<String> newDirectorsId = getDirectorsId();
        film.setDirectors(newDirectorsId);
    }

    private void setActors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        LinkedList<String> newActorsId = getActorsId();
        film.setActors(newActorsId);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
