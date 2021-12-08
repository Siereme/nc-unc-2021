package view.find;

import controller.FilmController;
import controller.GenreController;
import model.Genre.Genre;
import view.IView;
import view.View;

import java.util.LinkedList;

public class FindByGenreView extends View implements IView {
    GenreController genreController = new GenreController();

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
                Genre genre = genreController.getGenre(option);
                FilmController filmController = new FilmController();
                LinkedList<String> filmsId = filmController.getFilmsByGenre(genre);
                System.out.println(filmController.filmsByIdToString(filmsId));
                pressAnyKeyToContinue();
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
