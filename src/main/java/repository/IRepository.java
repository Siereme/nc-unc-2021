package repository;

import java.util.List;

public interface IRepository<T> {
    List<T> findAll();

    boolean deleteById(Integer Id);

    boolean findById(Integer Id);

    boolean create(T entity);

    void clear();

}
