package view.remove;

import controller.DirectorController;
import model.user.IUser;
import view.IView;
import view.View;

/** Command deletion director
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveDirectorView extends View implements IView {
    private final String name = "Delete Director";

    DirectorController directorController = new DirectorController();

    public RemoveDirectorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(directorController);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
