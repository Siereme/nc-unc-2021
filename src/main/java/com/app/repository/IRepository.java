package com.app.repository;

import javax.transaction.Transactional;
import java.util.List;

/** Repository interface
 * @author Sergey
 * @version 1.0
 * */
public interface IRepository<T> {
    /** Method gets list of entities
     * @return list of entities with type T
     * */
    List<T> findAll();

    @Transactional
    void add(T entity);

    void delete(int id);

    void edit(T entity);

    List<T> findByName(String name);

    int size();

    List<T> findByContains(String name);


}
