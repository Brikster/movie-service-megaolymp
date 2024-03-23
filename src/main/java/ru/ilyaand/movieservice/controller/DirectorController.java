package ru.ilyaand.movieservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ilyaand.movieservice.dto.director.DirectorListResponseDto;
import ru.ilyaand.movieservice.dto.director.DirectorRequestDto;
import ru.ilyaand.movieservice.dto.director.DirectorResponseDto;
import ru.ilyaand.movieservice.exception.CustomValidationException;
import ru.ilyaand.movieservice.model.Director;
import ru.ilyaand.movieservice.service.DirectorService;

import java.util.Optional;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getDirectors() {
        return ResponseEntity.ok(
                new DirectorListResponseDto(directorService.getAllDirectors()));
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getDirector(@PathVariable Long id) {
        Optional<Director> directorOptional = directorService.getById(id);
        if (directorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new DirectorResponseDto(directorOptional.get()));
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createDirector(@Valid @RequestBody DirectorRequestDto body) {
        Director created = directorService.createDirector(body.getDirector());
        return ResponseEntity.ok(new DirectorResponseDto(created));
    }

    @PatchMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> updateDirector(@PathVariable Long id,
                                            @Valid @RequestBody DirectorRequestDto body) {

        if (body.getDirector().getId() != null) {
            throw new CustomValidationException("director.id: must be null");
        }

        Optional<Director> updated = directorService.updateDirector(id, body.getDirector());
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DirectorResponseDto(updated.get()));
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        boolean removed = directorService.deleteById(id);
        if (removed) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
