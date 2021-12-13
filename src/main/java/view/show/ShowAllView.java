package view.show;

import controller.commands.show.ShowUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

public class ShowAllView extends View implements IView {
    public static final String SHOW_ALL_MENU = "------Show All Menu------";
    private final String name = "Show all ...";


    public ShowAllView(IUser currentUser) {
        super(currentUser);
    }


    @Override
    public void display() {
        ShowUserCommands commands = new ShowUserCommands();
        drawSubMenu(commands.commands, SHOW_ALL_MENU);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
