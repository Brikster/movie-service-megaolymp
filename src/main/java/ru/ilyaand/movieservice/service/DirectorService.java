package ru.ilyaand.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyaand.movieservice.model.Director;
import ru.ilyaand.movieservice.repository.DirectorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Optional<Director> getById(Long id) {
        return directorRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        int updated = directorRepository.deleteById(id);
        return updated != 0;
    }

    public void deleteAll() {
        directorRepository.deleteAll();
    }

    @Transactional
    public Director createDirector(Director director) {
        Long id;
        if (director.getId() == null) {
            id = directorRepository.insert(director);
        } else {
            id = directorRepository.insertWithId(director);
        }
        return directorRepository.findById(id).orElseThrow();
    }

    public Optional<Director> updateDirector(Long id, Director director) {
        director.setId(id);
        int updated = directorRepository.update(director);
        if (updated == 0) {
            return Optional.empty();
        }
        return Optional.of(director);
    }

}
