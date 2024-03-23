CREATE TABLE movieservice_directors (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    fio VARCHAR(100) NOT NULL
);

CREATE TABLE movieservice_movies (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(100) NOT NULL,
    year NUMERIC(4, 0) NOT NULL
        CHECK ( year >= 1900 AND year <= 2100 ),
    director BIGINT NOT NULL
        REFERENCES movieservice_directors (id),
    length INTERVAL NOT NULL,
    rating NUMERIC(2, 0) NOT NULL
        CHECK ( rating >= 0 AND rating <= 10 )
);