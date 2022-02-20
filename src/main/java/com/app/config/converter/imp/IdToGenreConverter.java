package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.genre.Genre;
import com.app.repository.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToGenreConverter extends IdToEntityConverter<Object, Genre> {

    public IdToGenreConverter(GenresRepository repository) {
        super(repository);
    }

//    @Autowired
//    GenresRepository repository;
//
//    @Override
//    public Genre convert(Object source) {
//        Integer id = Integer.parseInt((String)source);
//        return repository.findById((int) id);
//    }
}
