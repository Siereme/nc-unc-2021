package view.find;

import controller.FilmController;
import controller.GenreController;
import model.genre.Genre;
import model.user.IUser;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Command find films by genre
 * @author Vasiliy
 * @version 1.0
 * */
public class FindByGenreView extends View implements IView {

    public String name = "Find By Genre";


    GenreController genreController = new GenreController();

    public FindByGenreView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Find By Genre Menu------");
            System.out.println("------Select Genre------");
            System.out.println(genreController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option < genreController.size() && option >= 0) {
                Genre genre = genreController.getEntity(option);
                FilmController filmController = new FilmController();
                LinkedList<String> filmsId = filmController.getFilmsByGenre(genre);
                System.out.println(filmController.entitiesByIDsToString(filmsId));
                pressAnyKeyToContinue();
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
