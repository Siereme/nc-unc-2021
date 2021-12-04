package com.gmail.viktor.yuryev.mvc.console.repository;

import com.gmail.viktor.yuryev.mvc.console.model.Film;

import java.util.List;

public interface IRepository<T> {
    List<T> findAll();

    boolean deleteById(Integer Id);

    boolean findById(Integer Id);

    boolean create(T entity);
}
