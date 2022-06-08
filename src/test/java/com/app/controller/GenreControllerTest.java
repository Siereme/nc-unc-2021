package com.app.controller;

import com.app.model.genre.Genre;
import com.app.repository.GenresRepository;
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
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenresRepository genresRepository;

    private List<Genre> genreList = new ArrayList<>();

    @BeforeAll
    void initGenres() {
        this.genreList.addAll(List.of(new Genre("genre1"), new Genre("genre2"), new Genre("genre3")));
        for (int i = 0; i < genreList.size(); ++i) {
            genreList.get(i).setId(i);
        }
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(genresRepository.findAll()).thenReturn(genreList);

        mvc.perform(MockMvcRequestBuilders.get("/genres/all")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("genres"))
                .andExpect(MockMvcResultMatchers.model().attribute("genres", Matchers.hasSize(3)));

        Mockito.verify(genresRepository).findAll();
    }

    @Test
    void testSearch() throws Exception {
        Mockito.when(genresRepository.findByContains("genre")).thenReturn(genreList);

        mvc.perform(MockMvcRequestBuilders.post("/genres/find").param("tittle", "genre"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("genres"))
                .andExpect(MockMvcResultMatchers.model().attribute("genres", Matchers.hasSize(3)));

        Mockito.verify(genresRepository).findByContains("genre");
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/genres/handle/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/genres/all"));

        Mockito.verify(genresRepository).delete(1);
    }

    @Test
    void testAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/genres/handle/add").flashAttr("genre", genreList.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/genres/all"));

        Mockito.verify(genresRepository).add(genreList.get(0));
    }

    @Test
    void testEdit() throws Exception {
        Genre genre = genreList.get(0);
        genre.setTittle("new title");
        mvc.perform(MockMvcRequestBuilders.post("/genres/handle/edit").flashAttr("genre", genre))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/genres/all"));

        Mockito.verify(genresRepository).edit(genre);
        Assertions.assertEquals("new title", genre.getTittle());
    }

}
