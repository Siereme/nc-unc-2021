package com.app.repository.actor;

import com.app.model.actor.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorRepository extends MongoRepository<Actor, Long> {
}
