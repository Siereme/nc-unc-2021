package app.view.remove;

import app.controller.commands.remove.RemoveUserCommands;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Class removing commands
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class RemoveView extends View implements IView {
    /** String that user will see */
    public static final String DELETE_MENU = "------Delete Menu------";

    public RemoveView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        RemoveUserCommands commands = new RemoveUserCommands();
        drawSubMenu(commands.commands, DELETE_MENU);
    }

    public String getName() {
        return "Delete anything ...";
    }

    @Override
    public void showMessage(String messsage) {

    }
}
