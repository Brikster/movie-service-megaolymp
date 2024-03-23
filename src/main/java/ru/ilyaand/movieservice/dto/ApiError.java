package ru.ilyaand.movieservice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private final int status;
    private final String reason;

    public ApiError(HttpStatus status, String reason) {
        this.status = status.value();
        this.reason = reason;
    }

}
