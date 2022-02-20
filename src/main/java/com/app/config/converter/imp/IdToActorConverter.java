package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToActorConverter extends IdToEntityConverter<Object, Actor> {

    public IdToActorConverter(ActorsRepository repository) {
        super(repository);
    }

//    @Autowired
//    ActorsRepository repository;
//
//    @Override
//    public Actor convert(Object source) {
//        Integer id = Integer.parseInt((String)source);
//        return repository.findById((int) id);
//    }

}
