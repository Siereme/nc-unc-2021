package app.view.find;

import app.controller.commands.find.FindUserCommands;
import app.model.user.IUser;
import app.view.IView;
import app.view.View;

/** Class menu of searching commands
 * @author Vasiliy
 * @version 1.0
 * */
public class    FindView extends View implements IView {
    public static final String FIND_MENU = "------Find Menu------";

    public FindView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        FindUserCommands commands = new FindUserCommands();
        drawSubMenu(commands.commands, FIND_MENU);
    }

    public String getName(){
        return "Find film by ...";
    }

}
