package ru.ilyaand.movieservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ilyaand.movieservice.DatabaseTestConfiguration;
import ru.ilyaand.movieservice.model.Movie;
import ru.ilyaand.movieservice.service.MovieService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@Import(DatabaseTestConfiguration.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService service;

    @Test
    public void testAllMovies()
            throws Exception {

        Movie movie = new Movie(1L, "Test Movie", 2015, 1L, Duration.ofMinutes(122), 2);

        given(service.getAllMovies()).willReturn(List.of(movie));

        mvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(1)))
                .andExpect(jsonPath("$.list[0].title", is("Test Movie")));
    }

    @Test
    public void testOneMovieExists()
            throws Exception {

        Movie movie = new Movie(1L, "Test Movie", 2015, 1L, Duration.ofMinutes(122), 2);

        given(service.getById(1L)).willReturn(Optional.of(movie));

        mvc.perform(get("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie.title", is("Test Movie")));
    }

    @Test
    public void testOneMovieNotExists()
            throws Exception {
        given(service.getById(1L)).willReturn(Optional.empty());

        mvc.perform(get("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}