package com.app.config.converter;

import com.app.repository.AbstractRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


public abstract  class IdToEntityConverter<Object, T> implements Converter<Object, T> {

    AbstractRepository<T> repository;

    public IdToEntityConverter(AbstractRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public T convert(Object source) {
        Integer id = Integer.parseInt((String)source);
        return repository.findById((int) id);
    }
}
