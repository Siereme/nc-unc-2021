package view;

import controller.commands.main.MainUserCommands;
import model.user.IUser;

/**
 * Класс главное меню
 * @author vasily
 * @version 1.0
 * */
public class MainMenuView extends View {
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
