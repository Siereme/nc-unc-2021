package view.edit.genre;

import controller.GenreController;
import model.genre.Genre;
import model.user.IUser;
import view.IView;
import view.View;

/** Класс команда изменения жанров
 * @author Vasiliy
 * @version 1.0
 * */
public class EditGenreMenuView extends View implements IView {
    /** Поле название команды */
    private final String name = "Edit Genre";

    /** Поле контроллер для жанров */
    private final GenreController genreController = new GenreController();

    public EditGenreMenuView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("Select Genre to Edit");
            System.out.println("-1. Exit");
            System.out.println(genreController);
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option >= 0 && option < genreController.size()) {
                int genreInd = option;
                Genre genre = genreController.getEntity(genreInd);
                System.out.println(genreController.entityToString(genre));
                System.out.println("1. Change Tittle");
                System.out.println("2. Exit");
                option = getOption();
                switch (option) {
                    case 1:
                        setTittle(genreInd);
                        genreController.updateRepository();
                        break;
                    case 2:
                        break;
                }

            }

        }
    }

    public String getName(){
        return this.name;
    }

    private void setTittle(int genreInd) {
        Genre genre = genreController.getEntity(genreInd);
        String newTittle = getStr("Enter a new Tittle\n");
        genre.setTittle(newTittle);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
