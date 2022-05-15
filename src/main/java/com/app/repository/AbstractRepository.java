package com.app.repository;

import com.app.model.IEntity;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractRepository<T> implements MongoRepository<T, Integer> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public abstract T findById(int id);

}
