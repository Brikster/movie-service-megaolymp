package ru.ilyaand.movieservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ilyaand.movieservice.dto.movie.MovieListResponseDto;
import ru.ilyaand.movieservice.dto.movie.MovieRequestDto;
import ru.ilyaand.movieservice.dto.movie.MovieResponseDto;
import ru.ilyaand.movieservice.exception.CustomValidationException;
import ru.ilyaand.movieservice.model.Movie;
import ru.ilyaand.movieservice.service.MovieService;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getMovies() {
        return ResponseEntity.ok(
                new MovieListResponseDto(movieService.getAllMovies()));
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getById(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new MovieResponseDto(movieOptional.get()));
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieRequestDto body) {
        Movie created = movieService.createMovie(body.getMovie());
        return ResponseEntity.ok(new MovieResponseDto(created));
    }

    @PatchMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                         @Valid @RequestBody MovieRequestDto body) {
        if (body.getMovie().getId() != null) {
            throw new CustomValidationException("movie.id: must be null");
        }

        Optional<Movie> updated = movieService.updateMovie(id, body.getMovie());

        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MovieResponseDto(updated.get()));
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        boolean removed = movieService.deleteById(id);
        if (removed) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
