package com.app.repository;

import com.app.model.actor.Actor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorsRepositoryTest {

    @Mock
    private ActorsRepository repository;
    private List<Actor> actors = new ArrayList<>();

    @BeforeAll
    void initActors() {
        this.actors.addAll(List.of(new Actor("actor1", "21"), new Actor("actor2", "22"), new Actor("actor3", "23")));
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).setId(i);
        }
    }

    @Test
    @Order(2)
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(actors);
        List<Actor> actorList = repository.findAll();

        Mockito.verify(repository).findAll();
        Assertions.assertEquals(3, actorList.size());
        Assertions.assertEquals("actor1", actorList.get(0).getName());
        Assertions.assertEquals("actor2", actorList.get(1).getName());
        Assertions.assertEquals("actor3", actorList.get(2).getName());
    }

    @Test
    @Order(3)
    void testFindById() {
        Actor actor = actors.get(1);

        Mockito.when(repository.findById(1)).thenReturn(actor);
        Actor actorFind = repository.findById(1);

        Mockito.verify(repository).findById(1);
        Assertions.assertEquals(1, actorFind.getId());
        Assertions.assertEquals("actor2", actorFind.getName());
    }

    @Test
    @Order(4)
    void find() {
        Mockito.when(repository.find(List.of(0, 1, 2))).thenReturn(actors);

        List<Actor> actorList = repository.find(List.of(0, 1, 2));

        Mockito.verify(repository).find(List.of(0, 1, 2));
        Assertions.assertEquals(3, actorList.size());
        Assertions.assertEquals("actor1", actorList.get(0).getName());
        Assertions.assertEquals("actor2", actorList.get(1).getName());
        Assertions.assertEquals("actor3", actorList.get(2).getName());
    }

    @Test
    @Order(1)
    void testAdd() {
        Actor actor = new Actor("actor4", "24");

        Mockito.when(repository.add(actor)).thenReturn(actor);
        repository.add(actor);

        Mockito.verify(repository).add(actor);
        Assertions.assertEquals("actor4", actor.getName());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Mockito.verify(repository).delete(1);

        Actor actor = repository.findById(2);
        Assertions.assertNull(actor);
    }

    @Test
    @Order(8)
    void testEdit() {
        Actor actor = actors.get(1);
        actor.setName("new name");

        repository.edit(actor);

        Mockito.verify(repository).edit(actor);
        Assertions.assertEquals("new name", actor.getName());
    }

    @Test
    @Order(5)
    void testFindByName() {
        Actor actor = actors.get(0);

        Mockito.when(repository.findByName("actor1")).thenReturn(List.of(actor));
        List<Actor> actors = repository.findByName("actor1");

        Mockito.verify(repository).findByName("actor1");
        Assertions.assertEquals("actor1", actors.get(0).getName());
    }

    @Test
    @Order(6)
    void testSize() {
        Mockito.when(repository.size()).thenReturn(3);
        int size = repository.size();

        Mockito.verify(repository).size();
        Assertions.assertEquals(3, size);
    }

    @Test
    @Order(7)
    void testFindByContains() {
        Mockito.when(repository.findByContains("actor")).thenReturn(actors);
        List<Actor> actorList = repository.findByContains("actor");

        Mockito.verify(repository).findByContains("actor");
        Assertions.assertFalse(actorList.isEmpty());
        Assertions.assertEquals(3, actorList.size());
        Assertions.assertEquals("actor1", actorList.get(0).getName());
        Assertions.assertEquals("actor2", actorList.get(1).getName());
        Assertions.assertEquals("actor3", actorList.get(2).getName());
    }

}
