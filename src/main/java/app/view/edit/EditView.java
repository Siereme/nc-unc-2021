package app.view.edit;

import app.controller.commands.edit.EditUserCommands;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Class menu of changing command
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class EditView extends View implements IView {
    public static final String EDIT_MENU = "------Edit Menu------";

    public EditView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        EditUserCommands commands = new EditUserCommands();
        drawSubMenu(commands.commands, EDIT_MENU);
    }

    public String getName(){
        return "Edit anything ...";
    }

}
