package view.remove;

import view.IView;
import view.View;

public class RemoveView extends View implements IView {
    RemoveActorView removeActorView = new RemoveActorView();
    RemoveFilmView removeFilmView = new RemoveFilmView();
    RemoveGenreView removeGenreView = new RemoveGenreView();
    RemoveDirectorView removeDirectorView = new RemoveDirectorView();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Menu------");
            System.out.println("1. Remove Film");
            System.out.println("2. Remove Genre");
            System.out.println("3. Remove Actor");
            System.out.println("4. Remove Director");
            System.out.println("5. Exit");
            int option = getOption();
            switch (option) {
                case 1:
                    removeFilmView.display();
                    break;
                case 2:
                    removeGenreView.display();
                    break;
                case 3:
                    removeActorView.display();
                    break;
                case 4:
                    removeDirectorView.display();
                    break;
                case 5:
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
