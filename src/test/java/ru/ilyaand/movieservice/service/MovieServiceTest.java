package ru.ilyaand.movieservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilyaand.movieservice.exception.CustomIntegrityException;
import ru.ilyaand.movieservice.model.Director;
import ru.ilyaand.movieservice.model.Movie;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @BeforeAll
    void setUpAll() {
        movieService.deleteAll();
        directorService.deleteAll();
        directorService.createDirector(new Director(1L, "Ivanov Ivan Ivanovich"));
    }

    @BeforeEach
    void setUpEach() {
        movieService.deleteById(1L);
        movieService.deleteById(2L);
        movieService.createMovie(new Movie(1L, "Awesome Movie",
                2015, 1L, Duration.ofHours(122), 5));
    }

    @Test
    void getAllMovies() {
        assertDoesNotThrow(() -> {
            movieService.getAllMovies();
        });
        assertEquals(1, movieService.getAllMovies().size());
    }

    @Test
    void getById() {
        Optional<Movie> movieOptional = movieService.getById(1L);
        assertTrue(movieOptional.isPresent());
        assertEquals("Awesome Movie", movieOptional.get().getTitle());

        Optional<Movie> movieOptionalId2 = movieService.getById(2L);
        assertFalse(movieOptionalId2.isPresent());
    }

    @Test
    void deleteById() {
        Optional<Movie> movieOptional = movieService.getById(1L);
        assertTrue(movieOptional.isPresent());

        movieService.deleteById(1L);

        Optional<Movie> movieOptionalSecond = movieService.getById(1L);
        assertFalse(movieOptionalSecond.isPresent());
    }

    @Test
    void createMovie() {
        assertThrows(CustomIntegrityException.class, () -> {
            movieService.createMovie(new Movie(2L, "Catch Me If You Can",
                    2000, 2L, Duration.ofHours(165), 10));
        });

        assertDoesNotThrow(() -> {
            movieService.createMovie(new Movie(2L, "Catch Me If You Can",
                    2000, 1L, Duration.ofHours(165), 10));
        });

        Optional<Movie> movieOptional = movieService.getById(2L);
        assertTrue(movieOptional.isPresent());
        assertEquals("Catch Me If You Can", movieOptional.get().getTitle());
    }

    @Test
    void updateMovie() {
        Optional<Movie> movieOptional = movieService.getById(1L);
        assertTrue(movieOptional.isPresent());
        assertEquals(2015, movieOptional.get().getYear());

        Movie movie = movieOptional.get();
        movie.setYear(2010);

        movieService.updateMovie(1L, movie);

        Optional<Movie> movieOptionalSecond = movieService.getById(1L);
        assertTrue(movieOptionalSecond.isPresent());
        assertEquals(2010, movieOptionalSecond.get().getYear());
    }
}