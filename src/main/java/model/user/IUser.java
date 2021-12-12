package model.user;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.user.Admin.Admin;
import model.user.Visitor.Visitor;

/** Интерфейс пользователя
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Visitor.class, name = "visitor") }
)
public interface IUser {
    /** Метод получения id пользователя
     * @return возвращает id пользователя
     * */
    String getId();

    /** Метод получения имени пользователя
     * @return возвращает строку - имя пользователя
     * */
    String getName();

    /** Метод установки нового имени пользователя
     * @param name - новое имя пользователя
     * */
    void setName(String name);

    /** Метод получения пароля пользователя
     * @return возвращает пароль пользователя
     * */
    String getPassword();

    /** Метод установки нового пароля пользователю
     * @param password - новый пароль пользователя
     * */
    void setPassword(String password);

    /** Метод не используется, информация не известна */
    Boolean getRole();
}
