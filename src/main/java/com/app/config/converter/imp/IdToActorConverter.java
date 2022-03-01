package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import org.springframework.stereotype.Component;

@Component
public class IdToActorConverter extends IdToEntityConverter<Object, Actor> {

    public IdToActorConverter(ActorsRepository repository) {
        super(repository);
    }

}
