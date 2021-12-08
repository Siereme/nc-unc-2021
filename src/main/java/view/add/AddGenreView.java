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
                genreController.getGenreRepository().findAll().add(new Genre("no name"));
                int ind = genreController.getGenreRepository().findAll().size() - 1;
                setTittle(ind);
            }
        }
    }

    public String getName(){
        return this.name;
    }

    void setTittle(int genreInd) {
        genreController.getGenreRepository().findAll().get(genreInd).setTittle(getStr("Enter a tittle\n"));
    }

    @Override
    public void showMessage(String messsage) {

    }
}
