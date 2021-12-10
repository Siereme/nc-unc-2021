package view.show;

import controller.DirectorController;
import view.View;

public class ShowAllDirectorsView extends View {
    /** Поле название команды */
    public String name = "Show All Directors";

    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        System.out.println(directorController);
        pressAnyKeyToContinue();
    }

    @Override
    public void showMessage(String messsage) {

    }
}
