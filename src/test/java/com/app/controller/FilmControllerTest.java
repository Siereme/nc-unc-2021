package com.app.controller;

import com.app.config.WebSecurityTestConfig;
import com.app.model.film.Film;
import com.app.repository.FilmsRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    private FilmsRepository filmsRepository;



    private List<Film> films = new ArrayList<>();

    @BeforeAll
    void initFilms(){
        this.films.addAll(List.of(
                new Film("film1", LocalDate.of(2022, 6, 2)),
                new Film("film2", LocalDate.of(2022, 6, 2)),
                new Film("film3", LocalDate.of(2022, 6, 2))
        ));
        for (int i = 0; i < films.size(); i++) {
            films.get(i).setId(i);
        }
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void testFindAll() throws Exception {
        BDDMockito.given(filmsRepository.findAll()).willReturn(films);

        mvc.perform(MockMvcRequestBuilders.get("/films/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("films"))
                .andExpect(MockMvcResultMatchers.model().attribute("films", Matchers.hasSize(3)));

        BDDMockito.verify(filmsRepository).findAll();
    }

    @Test
    void testSearch() throws Exception {
        BDDMockito.given(filmsRepository.findByContains("film")).willReturn(films);

        mvc.perform(MockMvcRequestBuilders.post("/films/find").param("tittle", "film"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("films"))
                .andExpect(MockMvcResultMatchers.model().attribute("films", Matchers.hasSize(3)));

        BDDMockito.verify(filmsRepository).findByContains("film");
    }

    @Test
    @WithMockUser(username = "admin1", password = "admin1", roles = "ADMIN")
    void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/films/handle/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("film-handle"))
                .andExpect(MockMvcResultMatchers.model().attribute("films", Matchers.hasSize(2)));
    }

    @Test
    void testAdd() throws Exception {
//        BDDMockito.given(filmsRepository.add(films.get(0))).willReturn(films);

        mvc.perform(MockMvcRequestBuilders.post("/films/handle/add").requestAttr("film", films.get(0)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("film-handle"));

        BDDMockito.verify(filmsRepository).add(films.get(0));
    }

    @Test
    void testEdit() {
    }
}