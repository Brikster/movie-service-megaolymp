package ru.ilyaand.movieservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ilyaand.movieservice.DatabaseTestConfiguration;
import ru.ilyaand.movieservice.model.Director;
import ru.ilyaand.movieservice.service.DirectorService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DirectorController.class)
@Import(DatabaseTestConfiguration.class)
public class DirectorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DirectorService service;

    @Test
    public void testAllDirectors()
            throws Exception {

        Director director = new Director(1L, "Ivanov Ivan Ivanovich");

        given(service.getAllDirectors()).willReturn(List.of(director));

        mvc.perform(get("/api/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(1)))
                .andExpect(jsonPath("$.list[0].fio", is("Ivanov Ivan Ivanovich")));
    }

    @Test
    public void testOneDirectorExists()
            throws Exception {

        Director director = new Director(1L, "Ivanov Ivan Ivanovich");

        given(service.getById(1L)).willReturn(Optional.of(director));

        mvc.perform(get("/api/directors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.director.fio", is("Ivanov Ivan Ivanovich")));
    }

    @Test
    public void testOneDirectorNotExists()
            throws Exception {
        given(service.getById(1L)).willReturn(Optional.empty());

        mvc.perform(get("/api/directors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}