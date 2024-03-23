package ru.ilyaand.movieservice.repository;

import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.ilyaand.movieservice.model.Movie;

import java.util.List;
import java.util.Optional;

@JdbiRepository
@RegisterBeanMapper(Movie.class)
public interface MovieRepository {

    @SqlQuery("""
        SELECT *
        FROM movieservice_movies;
        """)
    List<Movie> findAll();

    @SqlQuery("""
        SELECT *
        FROM movieservice_movies
        WHERE id = :id;
        """)
    Optional<Movie> findById(Long id);

    @SqlUpdate("""
        DELETE FROM movieservice_movies
        WHERE id = :id;
        """)
    int deleteById(Long id);

    @SqlUpdate("""
        INSERT INTO movieservice_movies
        (title, year, director, length, rating)
        VALUES
        (:title, :year, :director, :length, :rating)
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long insert(@BindBean Movie movie);

    @SqlUpdate("""
        INSERT INTO movieservice_movies
        (id, title, year, director, length, rating)
        VALUES
        (:id, :title, :year, :director, :length, :rating)
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long insertWithId(@BindBean Movie movie);

    @SqlUpdate("""
        UPDATE movieservice_movies
        SET
            title = :title,
            year = :year,
            director = :director,
            length = :length,
            rating = :rating
        WHERE id = :id;
        """)
    int update(@BindBean Movie movie);

    @SqlUpdate("""
        INSERT INTO movieservice_movies
        (id, title, year, director, length, rating)
        VALUES
        (:id, :title, :year, :director, :length, :rating)
        ON CONFLICT (id) DO UPDATE
        SET
            title = :title,
            year = :year,
            director = :director,
            length = :length,
            rating = :rating
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long upsert(@BindBean Movie movie);

    @SqlUpdate("""
        DELETE FROM movieservice_movies WHERE TRUE;
        """)
    void deleteAll();
}
