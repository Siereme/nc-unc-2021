package view.edit.genre;

import controller.GenreController;
import view.IView;
import view.View;

public class EditGenreMenuView extends View implements IView {
    private final String name = "Edit Genre";
    private final GenreController genreController = new GenreController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("Select Genre to Edit");
            System.out.println("-1. Exit");
            System.out.println(genreController.getGenreRepository().toString());
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option >= 0 && option < genreController.getGenreRepository().findAll().size()) {
                int genreInd = option;
                System.out.println(genreController.getGenreRepository().findAll().get(genreInd));
                System.out.println("1. Change Tittle");
                System.out.println("2. Exit");
                option = getOption();
                switch (option) {
                    case 1:
                        setTittle(genreInd);
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
        genreController.getGenreRepository().findAll().get(genreInd).setTittle(getStr("Enter a new Tittle\n"));
    }

    @Override
    public void showMessage(String messsage) {

    }
}
