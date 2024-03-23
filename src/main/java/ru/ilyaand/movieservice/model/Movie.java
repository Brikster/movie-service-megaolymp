package ru.ilyaand.movieservice.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    @Nullable Long id;
    @NotNull @Size(max = 100) String title;
    @NotNull @Min(1900) @Max(2100) Integer year;
    @NotNull Long director;
    @NotNull Duration length;
    @NotNull @Min(0) @Max(10) Integer rating;

}
