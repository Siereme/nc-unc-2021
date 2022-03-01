package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.genre.Genre;
import com.app.repository.GenresRepository;
import org.springframework.stereotype.Component;

@Component
public class IdToGenreConverter extends IdToEntityConverter<Object, Genre> {

    public IdToGenreConverter(GenresRepository repository) {
        super(repository);
    }

}
