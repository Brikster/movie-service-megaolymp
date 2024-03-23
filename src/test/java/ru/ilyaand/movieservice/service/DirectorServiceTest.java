package ru.ilyaand.movieservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilyaand.movieservice.model.Director;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectorServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @BeforeAll
    void setUpAll() {
        movieService.deleteAll();
        directorService.deleteAll();
    }

    @BeforeEach
    void setUpEach() {
        directorService.deleteById(1L);
        directorService.createDirector(new Director(1L, "Ivanov Ivan Ivanovich"));
        directorService.deleteById(2L);
    }

    @Test
    void getAllDirectors() {
        assertDoesNotThrow(() -> {
            directorService.getAllDirectors();
        });
        assertEquals(1, directorService.getAllDirectors().size());
    }

    @Test
    void getById() {
        Optional<Director> directorOptional = directorService.getById(1L);
        assertTrue(directorOptional.isPresent());
        assertEquals(directorOptional.get().getFio(), "Ivanov Ivan Ivanovich");

        assertFalse(directorService.getById(2L).isPresent());
    }

    @Test
    void deleteById() {
        Optional<Director> directorOptional = directorService.getById(1L);
        assertTrue(directorOptional.isPresent());

        directorService.deleteById(1L);

        Optional<Director> directorOptionalSecond = directorService.getById(1L);
        assertFalse(directorOptionalSecond.isPresent());
    }

    @Test
    void createDirector() {
        Optional<Director> directorOptional = directorService.getById(2L);
        assertFalse(directorOptional.isPresent());

        directorService.createDirector(new Director(2L, "Dmitriev Ivan Sergeevich"));

        Optional<Director> directorOptionalSecond = directorService.getById(2L);
        assertTrue(directorOptionalSecond.isPresent());
        assertEquals("Dmitriev Ivan Sergeevich", directorOptionalSecond.get().getFio());
    }

    @Test
    void updateDirector() {
        directorService.updateDirector(1L,
                new Director(null, "Dmitriev Ivan Sergeevich"));

        Optional<Director> directorOptional = directorService.getById(1L);
        assertTrue(directorOptional.isPresent());
        assertEquals("Dmitriev Ivan Sergeevich", directorOptional.get().getFio());
    }
}