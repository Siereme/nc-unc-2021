package com.app.repository;

import com.app.model.actor.Actor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ActorsRepositoryTest {

    @Autowired
    ActorsRepository actorsRepository;

    @Test
    void testFindAll() {
        List<Actor> actorList = actorsRepository.findAll();
        assertThat(actorList.isEmpty()).isFalse();
    }

    @Test
    void testFindById() {
        Actor actor = actorsRepository.findById(3);
        assertThat(actor.getId()).isEqualTo(3);
    }

    @Test
    void find() {
        List<Actor> actors = actorsRepository.find(List.of(1, 2, 3));
        assertThat(actors.size()).isEqualTo(3);
    }

    @Test
    void testAdd() {
        Actor actor = new Actor("actor", "25");

        actorsRepository.add(actor);

        Actor addedActor = actorsRepository.findByName("actor").stream().findFirst().orElse(null);

        assertThat(addedActor).isNotEqualTo(null);
        assert addedActor != null;
        assertThat(addedActor.getName()).isEqualTo("actor");
        assertThat(addedActor.getYear()).isEqualTo("25");
    }

    @Test
    void testDelete() {
        actorsRepository.delete(3);

        Actor actor = actorsRepository.findById(3);

        assertThat(actor).isNull();
    }

    @Test
    void testEdit() {
        Actor actor = actorsRepository.findById(1);
        actor.setName("new name");

        actorsRepository.edit(actor);
        Actor editedActor = actorsRepository.findById(1);

        assertThat(editedActor.getName()).isEqualTo("new name");
    }

    @Test
    void testFindByName() {
        List<Actor> actors = actorsRepository.findByName("actor1");
        Actor actor = actors.stream().findFirst().orElse(null);

        assertThat(actor).isNotNull();
        assertThat(actor.getName()).isEqualTo("actor1");
    }

    @Test
    void testSize() {
    }

    @Test
    void testFindByContains() {
        List<Actor> actors = actorsRepository.findByContains("actor");
        assertThat(actors.isEmpty()).isFalse();
    }

}
