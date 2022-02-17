package com.app.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T> {

/*    @Resource(name = "entityManager")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }*/

    @PersistenceContext
    protected EntityManager entityManager;

/*    @Autowired
    protected SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

/*    @Autowired
    protected EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Resource(name = "entityManager")
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }*/

}
