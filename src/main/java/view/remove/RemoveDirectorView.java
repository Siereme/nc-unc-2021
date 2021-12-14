package view.remove;

import controller.DirectorController;
import model.user.IUser;
import view.IView;
import view.View;

/** Класс команда удаления режиссера
 * @author Vasiliy
 * @version 1.0
 * */
public class RemoveDirectorView extends View implements IView {
    /** Поле название команды */
    private final String name = "Delete Director";

    /** Поле контроллер для режиссеров */
    DirectorController directorController = new DirectorController();

    public RemoveDirectorView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Remove Director menu------");
            System.out.println("Select director to remove");
            System.out.println(directorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option >= 0 && option < directorController.getRepository().findAll().size()) {
                if (getConfirm()) {
                    directorController.removeEntity(option);
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
