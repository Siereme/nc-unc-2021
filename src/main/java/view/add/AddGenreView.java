package view.add;

import controller.GenreController;
import model.Genre.Genre;
import view.IView;
import view.View;

public class AddGenreView extends View implements IView {
    public String name = "Add Genre";
    private GenreController genreController = new GenreController();

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
                genreController.addEntity();
                int ind = genreController.size() - 1;
                setTittle(ind);
                genreController.updateRepository();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    void setTittle(int genreInd) {
        Genre genre = genreController.getEntity(genreInd);
        String newTittle = getStr("Enter a tittle\n");
        genre.setTittle(newTittle);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
