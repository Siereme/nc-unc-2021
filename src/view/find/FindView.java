package view.find;

import view.IView;
import view.View;

public class FindView extends View implements IView {
    private final FindByActorView findByActorView = new FindByActorView();
    private final FindByDirectorView findByDirectorView = new FindByDirectorView();
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
