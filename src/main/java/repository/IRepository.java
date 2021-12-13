package repository;

import java.io.IOException;
import java.util.List;

/** Интерфейс репозитория, от него будут наследованы все репозитории программа
 * @author Sergey
 * @version 1.0
 * */
public interface IRepository<T> {
    /** Метод возвращает список сущностей типа T
     * @return список сущностей типа T
     * */
    List<T> findAll();

    /** Метод удаляет сущность по её id
     * @param Id - id удаляемой сущности
     * @return значение true или false в зависимости от успешности удаления
     * */
    boolean deleteById(Integer Id);

    /** Метод осуществляет поиск сущности по её id
     * @param Id - id искомой сущности
     * @return значение true - если сущность была найдена и false - иначе
     * */
    boolean findById(Integer Id);

    /** Функция добавляет в репозиторий новую сущность
     * @param entity - сущность которая будет добавлена в репозиторий
     * @return значение true, если добавление успешно и false - иначе
     * */
    boolean create(T entity);

    /** Функция очищает репозиторий */
    void clear();

    /** Функция сериализует репозиторий в файл
     * @throws IOException
     * */
    void serialize() throws IOException;

    /** Функция десериализации из файла
     * @return список сущностей типа T, считанных из файла
     * */
    List<T> deserialize() throws IOException;

    /** Функция возвращает количество элементов в списке репозитория
     * @return возвращает количество элементов в списке репозитория
     * */
    int size();


}
