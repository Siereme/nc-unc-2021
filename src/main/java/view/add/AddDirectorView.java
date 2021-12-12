package view.add;

import controller.DirectorController;
import controller.FilmController;
import model.director.Director;
import view.IView;
import view.View;

import java.util.LinkedList;

/** Команда добавления режиссера
 * @author Vasiliy
 * @version 1.0
 * */
public class AddDirectorView extends View implements IView {
    /** Поле название команды */
    public String name = "Add Director";

    /** Поле контроллер для режиссеров */
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Adding Director menu------");
            System.out.println("1. Add director");
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option == 1) {
                // добавляем нового актера, а потом его редактируем
                Director director = new Director();
                setName(director);
                setYear(director);
                setFilms(director);
                directorController.addEntity(director);
                directorController.updateRepository();
            }
        }

    }

    public String getName() {
        return this.name;
    }

    void setName(Director director) {
        String newName = getStr("Enter Director Name\n");
        director.setName(newName);
    }

    void setYear(Director director) {
        String newAge = getStr("Enter Director Age\n");
        director.setYear(newAge);
    }

    void setFilms(Director director) {
        FilmController filmController = new FilmController();
        LinkedList<String> newFilmsId = getEntitiesId(filmController, "Select film to add\n");
        director.setFilms(newFilmsId);
        filmController.addDirectorToFilms(director, newFilmsId);
        filmController.updateRepository();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
