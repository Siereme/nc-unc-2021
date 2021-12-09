package view;

/**
 * Main View Interface
 *
 * @author vasiliy
 * @version 1.0
 */
public interface IView {
    /** Поле - меню, которое выводится пользователю */
    void display();

    /** функция выводит сообщение пользователю
     * @param messsage - сообщение, которое будет выведено
     * */
    void showMessage(String messsage);
}
