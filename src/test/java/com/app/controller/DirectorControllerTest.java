package com.app.controller;

import com.app.model.director.Director;
import com.app.repository.DirectorsRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DirectorControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private DirectorsRepository directorsRepository;

    private final List<Director> directors = new ArrayList<>();

    @BeforeAll
    void initDirectors() {
        this.directors.addAll(List.of(
                new Director("director1", "21"),
                new Director("director2", "22"),
                new Director("director3", "23")
        ));
        for (int i = 0; i < directors.size(); i++) {
            directors.get(i).setId(i);
        }
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(directorsRepository.findAll()).thenReturn(directors);

        mvc.perform(MockMvcRequestBuilders.get("/directors/all")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("directors"))
                .andExpect(MockMvcResultMatchers.model().attribute("directors", Matchers.hasSize(3)));

        Mockito.verify(directorsRepository).findAll();
    }

    @Test
    void testSearch() throws Exception {
        Mockito.when(directorsRepository.findByContains("director")).thenReturn(directors);

        mvc.perform(MockMvcRequestBuilders.post("/directors/find").param("tittle", "director"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("directors"))
                .andExpect(MockMvcResultMatchers.model().attribute("directors", Matchers.hasSize(3)));

        Mockito.verify(directorsRepository).findByContains("director");
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/directors/handle/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/directors/all"));

        Mockito.verify(directorsRepository).delete(1);
    }

    @Test
    void testAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/directors/handle/add").flashAttr("director", directors.get(0)))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/directors/all"));

        Mockito.verify(directorsRepository).add(directors.get(0));
    }

    @Test
    void testEdit() throws Exception {
        Director director = directors.get(0);
        director.setName("new name");
        mvc.perform(MockMvcRequestBuilders.post("/directors/handle/edit").flashAttr("director", director))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/directors/all"));

        Mockito.verify(directorsRepository).edit(director);
        Assertions.assertEquals("new name", director.getName());
    }


}
