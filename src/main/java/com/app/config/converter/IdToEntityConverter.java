package com.app.config.converter;

import com.app.model.IEntity;
import com.app.repository.AbstractRepository;
import com.app.repository.IRepository;
import org.springframework.core.convert.converter.Converter;


public abstract  class IdToEntityConverter<Object, T extends IEntity> implements Converter<Object, T> {

    final IRepository<T> repository;

    public IdToEntityConverter(IRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public T convert(Object source) {
        int id = Integer.parseInt((String)source);
        return repository.findById(id).orElse(null);
    }
}
