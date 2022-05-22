package com.app.service;

import com.app.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractService<T extends IEntity> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected abstract List<T> findByContains(String query);
}
