package view.add;

import view.IView;
import view.View;

public class AddView extends View implements IView {
    AddFilmView addFilmView = new AddFilmView();
    AddGenreView addGenreView = new AddGenreView();
    AddActorView addActorView = new AddActorView();
    AddDirectorView addDirectorView = new AddDirectorView();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Add Menu------");
            System.out.println("1. Add Film");
            System.out.println("2. Add Genre");
            System.out.println("3. Add Actor");
            System.out.println("4. Add Director");
            System.out.println("5. Exit");
            int option = getOption();
            switch (option) {
                case 1:
                    addFilmView.display();
                    break;
                case 2:
                    addGenreView.display();
                    break;
                case 3:
                    addActorView.display();
                    break;
                case 4:
                    addDirectorView.display();
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
