package com.example.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
