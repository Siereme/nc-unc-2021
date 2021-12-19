package app.view;

import app.controller.commands.main.MainUserCommands;
import app.model.user.IUser;

/**
 * Main menu class
 * @author vasily
 * @version 1.0
 * */
public class MainMenuView extends View {
    /** String that user will see*/
    public static final String MAIN_MENU = "------Main Menu------";



    public MainMenuView(IUser currentUser) {
        super(currentUser);
    }

    /**
     * @see View
     * */
    public void display() {
        MainUserCommands commands = new MainUserCommands();
        drawSubMenu(commands.commands, MAIN_MENU);
    }

    /**
     * @see View
     * */
    @Override
    public void showMessage(String messsage) {

    }

}
