package view.remove;

import controller.commands.remove.RemoveUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

/** Класс команд удаления сущностей
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class RemoveView extends View implements IView {
    public static final String DELETE_MENU = "------Delete Menu------";
    private String name = "Delete anything ...";

    public RemoveView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        RemoveUserCommands commands = new RemoveUserCommands();
        drawSubMenu(commands.commands, DELETE_MENU);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
