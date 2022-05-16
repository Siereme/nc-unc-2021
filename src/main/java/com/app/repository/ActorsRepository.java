package com.app.repository;

import com.app.model.actor.Actor;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Transactional
public interface ActorsRepository extends IRepository<Actor> {

}
