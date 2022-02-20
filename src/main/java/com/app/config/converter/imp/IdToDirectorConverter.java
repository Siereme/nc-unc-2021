package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.director.Director;
import com.app.repository.DirectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToDirectorConverter extends IdToEntityConverter<Object, Director> {

    public IdToDirectorConverter(DirectorsRepository repository) {
        super(repository);
    }

//    @Autowired
//    DirectorsRepository repository;

//    @Override
//    public Director convert(Object source) {
//        Integer id = Integer.parseInt((String)source);
//        return repository.findById((int) id);
//    }
}
