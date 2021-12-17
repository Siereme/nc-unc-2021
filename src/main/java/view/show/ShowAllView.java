package view.show;

import controller.commands.show.ShowUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

public class ShowAllView extends View implements IView {
    /** String that user will see*/
    public static final String SHOW_ALL_MENU = "------Show All Menu------";

    public ShowAllView(IUser currentUser) {
        super(currentUser);
    }


    @Override
    public void display() {
        ShowUserCommands commands = new ShowUserCommands();
        drawSubMenu(commands.commands, SHOW_ALL_MENU);
    }

    public String getName(){
        return "Show all ...";
    }

    @Override
    public void showMessage(String messsage) {

    }
}
