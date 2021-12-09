package view;

import view.add.AddView;
import view.edit.EditView;
import view.find.FindView;
import view.remove.RemoveView;

// TODO append from file
public class MainMenuView extends View {
    private final EditView editView = new EditView();
    private final FindView findView = new FindView();
    private final AddView addView = new AddView();
    private final RemoveView removeView = new RemoveView();

    public void display() {
        boolean show = true;
        while (show) {
            System.out.println();
            System.out.println("------MAIN MENU------");
            System.out.println("1. Edit anything ...");
            System.out.println("2. Find film by ...");
            System.out.println("3. Add anything ...");
            System.out.println("4. Delete anything ...");
            System.out.println("5. Exit");

            int option = getOption();
            switch (option) {
                case 1:
                    editView.display();
                    break;
                case 2:
                    findView.display();
                    break;
                case 3:
                    addView.display();
                    break;
                case 4:
                    removeView.display();
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
