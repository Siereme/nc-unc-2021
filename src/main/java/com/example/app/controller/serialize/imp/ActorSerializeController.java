package com.example.app.controller.serialize.imp;

import com.example.app.controller.serialize.AbstractSerializeController;
import com.example.app.model.actor.Actor;
import com.example.app.repository.ActorsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/serialize/actors")
public class ActorSerializeController extends AbstractSerializeController<Actor> {
    public static String ACTOR_FILE_PATH = "src/main/resources/database/Actors.json";

    public ActorSerializeController(ActorsRepository repository) {
        super(repository, ACTOR_FILE_PATH);
    }
}
