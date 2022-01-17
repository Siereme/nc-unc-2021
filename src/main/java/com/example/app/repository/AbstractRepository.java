package com.example.app.repository;

import com.example.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T>{
    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
