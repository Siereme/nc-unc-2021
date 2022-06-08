package com.app.controller;

import com.app.model.actor.Actor;
import com.app.repository.ActorsRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ActorsRepository actorsRepository;

    private List<Actor> actors = new ArrayList<>();

    @BeforeAll
    void initActors() {
        this.actors.addAll(List.of(new Actor("actor1", "21"), new Actor("actor2", "22"), new Actor("actor3", "23")));
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).setId(i);
        }
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(actorsRepository.findAll()).thenReturn(actors);

        mvc.perform(MockMvcRequestBuilders.get("/actors/all")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("actors"))
                .andExpect(MockMvcResultMatchers.model().attribute("actors", Matchers.hasSize(3)));

        Mockito.verify(actorsRepository).findAll();
    }

    @Test
    void testSearch() throws Exception {
        Mockito.when(actorsRepository.findByContains("actor")).thenReturn(actors);

        mvc.perform(MockMvcRequestBuilders.post("/actors/find").param("tittle", "actor"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("actors"))
                .andExpect(MockMvcResultMatchers.model().attribute("actors", Matchers.hasSize(3)));

        Mockito.verify(actorsRepository).findByContains("actor");
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/actors/handle/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/actors/all"));

        Mockito.verify(actorsRepository).delete(1);
    }

    @Test
    void testAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/actors/handle/add").flashAttr("actor", actors.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/actors/all"));

        Mockito.verify(actorsRepository).add(actors.get(0));
    }

    @Test
    void testEdit() throws Exception {
        Actor actor = actors.get(0);
        actor.setName("new name");
        mvc.perform(MockMvcRequestBuilders.post("/actors/handle/edit").flashAttr("actor", actor))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/actors/all"));

        Mockito.verify(actorsRepository).edit(actor);
        Assertions.assertEquals("new name", actor.getName());
    }

}
