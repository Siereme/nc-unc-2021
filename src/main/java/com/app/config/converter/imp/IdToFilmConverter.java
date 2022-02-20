package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.film.Film;
import com.app.repository.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToFilmConverter extends IdToEntityConverter<Object, Film> {

    public IdToFilmConverter(FilmsRepository repository) {
        super(repository);
    }

//    @Autowired
//    FilmsRepository repository;
//
//    @Override
//    public Film convert(Object source) {
//        Integer id = Integer.parseInt((String)source);
//        return repository.findById(id);
//    }
}
