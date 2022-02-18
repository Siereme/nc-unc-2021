package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/actors")
public class ActorSerializeController extends AbstractSerializeController<Actor> {
    private final String filePath = "src/main/resources/database/Actors.json";

    @Autowired
    private ActorsRepository actorsRepository;

    @Override
    @PostConstruct
    protected void getRepository() {
        super.repository = actorsRepository;
    }

    @Override
    @PostConstruct
    protected void getFilePath() {
        super.filePath = filePath;
    }

    @Override
    protected TypeReference<List<Actor>> getRef() {
        return new TypeReference<List<Actor>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/actors/all";
    }

    @Override
    protected List<String> checkErrors(List<Actor> entityList) {
        return null;
    }

}
