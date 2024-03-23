package ru.ilyaand.movieservice;

import org.jdbi.v3.spring5.EnableJdbiRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJdbiRepositories
public class MovieserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieserviceApplication.class, args);
    }

}
