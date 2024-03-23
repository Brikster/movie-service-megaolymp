package ru.ilyaand.movieservice.repository;

import org.jdbi.v3.spring5.JdbiRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.ilyaand.movieservice.model.Director;

import java.util.List;
import java.util.Optional;

@JdbiRepository
@RegisterBeanMapper(Director.class)
public interface DirectorRepository {

    @SqlQuery("""
        SELECT *
        FROM movieservice_directors;
        """)
    List<Director> findAll();

    @SqlQuery("""
        SELECT *
        FROM movieservice_directors
        WHERE id = :id;
        """)
    Optional<Director> findById(Long id);

    @SqlUpdate("""
        DELETE FROM movieservice_directors
        WHERE id = :id;
        """)
    int deleteById(Long id);

    @SqlUpdate("""
        INSERT INTO movieservice_directors
        (fio)
        VALUES
        (:fio)
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long insert(@BindBean Director director);

    @SqlUpdate("""
        INSERT INTO movieservice_directors
        (id, fio)
        VALUES
        (:id, :fio)
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long insertWithId(@BindBean Director director);

    @SqlUpdate("""
        UPDATE movieservice_directors
        SET fio = :fio
        WHERE id = :id;
        """)
    int update(@BindBean Director director);

    @SqlUpdate("""
        INSERT INTO movieservice_directors
        (id, fio)
        VALUES
        (:id, :fio)
        ON CONFLICT (id) DO UPDATE
        SET fio = :fio
        RETURNING id;
        """)
    @GetGeneratedKeys
    Long upsert(@BindBean Director director);

    @SqlUpdate("""
        DELETE FROM movieservice_directors WHERE TRUE;
        """)
    void deleteAll();
}
