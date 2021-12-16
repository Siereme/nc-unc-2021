package view.edit;

import controller.commands.edit.EditUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

/** Class menu of changing command
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class EditView extends View implements IView {
    public static final String EDIT_MENU = "------Edit Menu------";
    private String name = "Edit anything ...";

    public EditView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        EditUserCommands commands = new EditUserCommands();
        drawSubMenu(commands.commands, EDIT_MENU);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
