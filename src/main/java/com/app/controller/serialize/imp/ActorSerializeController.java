package com.app.controller.serialize.imp;

import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import com.app.controller.serialize.AbstractSerializeController;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/serialize/actors")
public class ActorSerializeController extends AbstractSerializeController<Actor> {
    public static String ACTOR_FILE_PATH = "src/main/resources/database/Actors.json";

    public ActorSerializeController(ActorsRepository repository) {
        super(repository, ACTOR_FILE_PATH);
    }

    @Override
    protected TypeReference<List<Actor>> getRef() {
        return new TypeReference<List<Actor>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/actors/all";
    }

}
