package view.find;

import controller.commands.find.FindUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

/** Класс меню команд поиска
 * @author Vasiliy
 * @version 1.0
 * */
public class FindView extends View implements IView {
    public static final String FIND_MENU = "------Find Menu------";
    private String name = "Find film by ...";

    public FindView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        FindUserCommands commands = new FindUserCommands();
        drawSubMenu(commands.commands, FIND_MENU);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
