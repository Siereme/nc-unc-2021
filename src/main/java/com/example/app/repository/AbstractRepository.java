package com.example.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    protected EntityManager entityManager;

/*    @Autowired
    protected EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Resource(name = "entityManager")
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }*/


    protected MapSqlParameterSource parameters = new MapSqlParameterSource();

}
