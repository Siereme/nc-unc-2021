package com.app.model.user;


import com.app.model.IEntity;

/** User Interface
 * @author Sergey
 * @version 1.0
 * */

public interface IUser extends IEntity {
    /** Method gets user id
     * @return user id
     * */
    int getId();

    /** Method gets user name
     * @return string value of the username
     * */
    String getName();

    /** Method sets username
     * @param username - new value of the username
     * */
    void setUsername(String username);

    /** Method gets user password
     * @return string value of the user password
     * */
    String getPassword();

    /** Method sets new value of the user password
     * @param password - new value of the user password
     * */
    void setPassword(String password);

    /** Method checks whether the user is an administrator
     * @return true - if user is administrator, false - else
     * */
    Boolean isAdmin();
}
