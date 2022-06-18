package com.app.controller;

import com.app.model.film.Film;
import com.app.repository.FilmsRepository;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilmsRepository filmsRepository;

    private final List<Film> films = new ArrayList<>();

    @BeforeAll
    void initFilms(){
        this.films.addAll(List.of(
                new Film("film1", Date.valueOf("2009-03-28")),
                new Film("film2", Date.valueOf("2009-03-28")),
                new Film("film3", Date.valueOf("2009-03-28"))
        ));
        for (int i = 0; i < films.size(); i++) {
            films.get(i).setId(i);
        }
    }


    @Test
    void testFindAll() throws Exception {
        Mockito.when(filmsRepository.findAll()).thenReturn(films);

        mvc.perform(MockMvcRequestBuilders.get("/films/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("films"))
                .andExpect(MockMvcResultMatchers.model().attribute("films", Matchers.hasSize(3)));

        Mockito.verify(filmsRepository).findAll();
    }

    @Test
    void testSearch() throws Exception {
        Mockito.when(filmsRepository.findByContains("film")).thenReturn(films);

        mvc.perform(MockMvcRequestBuilders.post("/films/find").param("tittle", "film"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("films"))
                .andExpect(MockMvcResultMatchers.model().attribute("films", Matchers.hasSize(3)));

        Mockito.verify(filmsRepository).findByContains("film");
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/films/handle/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/films/all"));

        Mockito.verify(filmsRepository).delete(1);
    }

    @Test
    void testAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/films/handle/add")
                        .flashAttr("film", films.get(0)))
                        .andExpect(MockMvcResultMatchers.view().name("redirect:/films/all"));

        Mockito.verify(filmsRepository).add(films.get(0));
    }

    @Test
//    @WithMockUser(username = "admin1", password = "admin1", roles = "ADMIN")
    void testEdit() throws Exception {
        Film film = films.get(0);
        film.setTittle("new title");
        mvc.perform(MockMvcRequestBuilders.post("/films/handle/edit")
                        .flashAttr("film", film))
                        .andExpect(MockMvcResultMatchers.view().name("redirect:/films/all"));

        Mockito.verify(filmsRepository).edit(film);
        Assertions.assertEquals("new title", film.getTittle());
    }
}