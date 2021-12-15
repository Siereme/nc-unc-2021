package view.edit.film;

import controller.ActorController;
import controller.DirectorController;
import controller.FilmController;
import controller.GenreController;
import model.film.Film;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.Date;
import java.util.LinkedList;

/** Класс команда изменения фильмов
 * @author Vasiliy
 * @version 1.0
 * */
public class EditFilmMenuView extends View implements IView {
    /** Поле название команды */
    private final String name = "Edit Film";

    /** Поле контроллер для фильмов */
    private final FilmController filmController = new FilmController();

    public EditFilmMenuView(IUser currentUser) {
        super(currentUser);
    }

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

    public String getName() {
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
        GenreController genreController = new GenreController();
        LinkedList<String> newGenresId = getEntitiesId(genreController, "Select Genres\n");
        film.setGenres(newGenresId);
    }

    private void setDirectors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        DirectorController directorController = new DirectorController();
        LinkedList<String> newDirectorsId = getEntitiesId(directorController, "Select Directors\n");
        film.setDirectors(newDirectorsId);
        directorController.removeFilmFromAllDirectors(film);
        directorController.addFilmToDirectors(film, newDirectorsId);
        directorController.updateRepository();
    }

    private void setActors(int filmInd) {
        Film film = filmController.getEntity(filmInd);
        ActorController actorController = new ActorController();
        LinkedList<String> newActorsId = getEntitiesId(actorController, "Select Actors\n");
        film.setActors(newActorsId);
        actorController.removeFilmFromAllActors(film);
        actorController.addFilmToActors(film, newActorsId);
        actorController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
