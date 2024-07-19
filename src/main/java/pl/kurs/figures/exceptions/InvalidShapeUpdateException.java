package pl.kurs.figures.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidShapeUpdateException extends RuntimeException {
    public InvalidShapeUpdateException(String message) {
        super(message);
    }
}
