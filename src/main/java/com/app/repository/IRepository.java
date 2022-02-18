package com.app.repository;

import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    void delete(int id);

    @Transactional
    void edit(T entity);

    List<T> findByName(String name);

    int size();

    List<T> findByContains(String name);


}
