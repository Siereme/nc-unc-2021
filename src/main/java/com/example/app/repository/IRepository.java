package com.example.app.repository;

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

    void add(T entity);

    void delete(int id);

    void edit(T entity);

    List<T> findByName(String name);

    int size();


}
