package view;

import view.add.AddView;
import view.edit.EditView;
import view.find.FindView;

// TODO append from file
public class MainMenuView extends View {
    private final EditView editView = new EditView();
    private final FindView findView = new FindView();
    private final AddView addView = new AddView();

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
                    // пока некорректно работает(надо
                    findView.display();
                    break;
                case 3:
                    // пока не провено работает ли вообще
                    addView.display();
                case 4:
                    System.out.println("not implemented\n");
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
