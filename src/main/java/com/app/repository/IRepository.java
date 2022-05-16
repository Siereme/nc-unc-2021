package com.app.repository;

import com.app.model.IEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/** Repository interface
 * @author Sergey
 * @version 1.0
 * */
public interface IRepository<T extends IEntity> extends MongoRepository<T, Integer> {
    /** Method gets list of entities
     * @return list of entities with type T
     * */
//    List<T> findAll();
//
//    @Transactional
//    T add(T entity);
//
//    @Transactional
//    void delete(int id);
//
//    @Transactional
//    void edit(T entity);
//
//    List<T> findByName(String name);
//
//
//    List<T> findByContains(String name);
//
//    T findById(int id);
}
