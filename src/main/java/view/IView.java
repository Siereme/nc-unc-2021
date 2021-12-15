package view;

/**
 * Main View Interface
 *
 * @author vasiliy
 * @version 1.0
 */
public interface IView {
    /** method creates menu that the user will see*/
    void display();

    /** method shows message to user
     * @param messsage - string value of the message that will be showed to user
     * */
    void showMessage(String messsage);
}
