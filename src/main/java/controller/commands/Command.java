package controller.commands;

/** Интерфейс - команда, от которого будут наследованы все классы команд
 * @author Sergey
 * @version 1.0
 * */
public interface Command {
    /** Функция получает имя команды
     * @return возвращает название команды
     * */
    String getName();

    /** Функция запускает команду */
    void execute();
}
