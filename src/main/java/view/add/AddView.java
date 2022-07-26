package view.add;

import controller.commands.add.AddUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

/** Menu of the adding commands
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class AddView extends View implements IView {
    private static final String ADD_MENU = "------Add Menu------";

    public AddView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        AddUserCommands commands = new AddUserCommands();
        drawSubMenu(commands.commands, ADD_MENU);
    }

    public String getName(){
        return "Add anything ...";
    }

    @Override
    public void showMessage(String messsage) {

    }
}
