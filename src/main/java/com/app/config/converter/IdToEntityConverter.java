package com.app.config.converter;

import com.app.repository.AbstractRepository;
import org.springframework.core.convert.converter.Converter;


public abstract  class IdToEntityConverter<Object, T> implements Converter<Object, T> {

    final AbstractRepository<T> repository;

    public IdToEntityConverter(AbstractRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public T convert(Object source) {
        int id = Integer.parseInt((String)source);
        return repository.findById(id);
    }
}
