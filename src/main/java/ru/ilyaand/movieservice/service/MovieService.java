package ru.ilyaand.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyaand.movieservice.exception.CustomIntegrityException;
import ru.ilyaand.movieservice.model.Movie;
import ru.ilyaand.movieservice.repository.DirectorRepository;
import ru.ilyaand.movieservice.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getById(Long id) {
        return movieRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        int updated = movieRepository.deleteById(id);
        return updated != 0;
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @Transactional
    public Movie createMovie(Movie movie) {
        if (directorRepository.findById(movie.getDirector()).isEmpty()) {
            throw new CustomIntegrityException("director with provided ID is not found");
        }
        Long id;
        if (movie.getId() == null) {
            id = movieRepository.insert(movie);
        } else {
            id = movieRepository.insertWithId(movie);
        }
        return movieRepository.findById(id).orElseThrow();
    }

    public Optional<Movie> updateMovie(Long id, Movie movie) {
        if (directorRepository.findById(movie.getDirector()).isEmpty()) {
            throw new CustomIntegrityException("director with provided ID is not found");
        }
        movie.setId(id);
        int updated = movieRepository.update(movie);
        if (updated == 0) {
            return Optional.empty();
        }
        return Optional.of(movie);
    }

}
