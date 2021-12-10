package controller;

import java.util.LinkedList;

/** Интерфейс контроллера
 * @author Vasiliy
 * @version 1.0
 * */
public interface IController<T> {

    /** Метод получения сущности по её id
     * @param id - id сущности, которую необходимо будет найти
     * @return возвращает объект типа сущности
     * */
    T getEntityById(String id);

    /** Метод приводит сущность к типу String
     * @param entity - сущность, которую метод преобразует в строку
     * @return возвращает строковое представление данной на вход сущности
     * */
    String entityToString(T entity);

    /** Метод 1) ищет сущность с id из списка ids, далее преобразует эту сущность в строку и так для каждого ids из списка
     * @param ids - список id сущностей, которые метод будет искать в репозитории
     * @return возвращает строковое представление всех найденных сущностей
     * */
    String entitiesByIDsToString(LinkedList<String> ids);

    /** Метод возвращает количество элементов списка репозитория
     * @see repository.IRepository
     * @return возвращает количество элементов списка репозитория
     * */
    int size();

    /** Получает сущность из репозитория по её индексу
     * @see repository.IRepository
     * @param ind - индекс сущности
     * @return возвращает найденную по индексу сущность
     * */
    T getEntity(int ind);

    /** Метод добавляет сущность в репозиторий, При этом сущность не содержит никакой полезной информации, то есть ее
     * необходимо будет отредактировать
     * @see repository.IRepository
     * */
    void addEntity();

    /** Метод обновляет репозиторий
     * @see repository.IRepository
     * */
    void updateRepository();

    /** Метод удаляет сущность из репозитория по её индексу
     * @param ind - индекс удаляемой сущности
     * @see repository.IRepository
     * */
    void removeEntity(int ind);

}
