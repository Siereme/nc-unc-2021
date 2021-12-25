package app.view.remove;

import app.controller.imp.DirectorController;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Command deletion director
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveDirectorView extends View implements IView {

    final DirectorController directorController = new DirectorController();

    public RemoveDirectorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        deleteAction(directorController);
    }

    public String getName() {
        return "Delete Director";
    }

}
