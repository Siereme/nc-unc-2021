package view.find;

import view.IView;
import view.View;

/** Класс меню команд поиска
 * @author Vasiliy
 * @version 1.0
 * */
public class FindView extends View implements IView {
    /** Поле команды поиска фильмов по актерам */
    private final FindByActorView findByActorView = new FindByActorView();

    /** Поле команды поиска фильмов по режиссерам */
    private final FindByDirectorView findByDirectorView = new FindByDirectorView();

    /** Поле команды поиска фильмов по жанрам */
    private final FindByGenreView findByGenreView = new FindByGenreView();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Find Menu------");
            System.out.println("1. Find film by actor");
            System.out.println("2. Find film by genre");
            System.out.println("3. Find film by director");
            System.out.println("4. Exit");
            int option = getOption();
            switch (option) {
                case 1:
                    findByActorView.display();
                    break;
                case 2:
                    findByGenreView.display();
                    break;
                case 3:
                    findByDirectorView.display();
                    break;
                case 4:
                    show = false;
                default:
                    break;
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
