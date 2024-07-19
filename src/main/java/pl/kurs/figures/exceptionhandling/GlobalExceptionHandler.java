package pl.kurs.figures.exceptionhandling;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.exceptions.InvalidIdException;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.exceptions.InvalidShapeUpdateException;

import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidShapeException.class)
    public ResponseEntity<ExceptionResponseDTO> handleShapeException(RuntimeException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "BAD_REQUEST",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler({InvalidEntityException.class})
    public ResponseEntity<ExceptionResponseDTO> handleEntityException(RuntimeException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "BAD_REQUEST",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDTO> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "BAD_REQUEST",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNoSuchFileException(NoSuchFileException e) {

        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of("Not found: " +e.getFile()),
                "BAD_REQUEST",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> fieldErrorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> "field: " + fe.getField() + " / rejected value: " + fe.getRejectedValue() + " / message: " + fe.getDefaultMessage())
                .toList();

        List<String> globalErrorMessages = e.getBindingResult().getGlobalErrors().stream()
                .map(ge -> "object: " + ge.getObjectName() + " / message: " + ge.getDefaultMessage())
                .toList();

        List<String> allErrorMessages = new ArrayList<>();
        allErrorMessages.addAll(fieldErrorMessages);
        allErrorMessages.addAll(globalErrorMessages);

        ExceptionResponseDTO response = new ExceptionResponseDTO(
                allErrorMessages,
                "BAD_REQUEST",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "NOT_FOUND",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidIdException(InvalidIdException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "NOT_FOUND",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidShapeUpdateException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidShapeUpdateException(InvalidShapeUpdateException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "BAD_REQUEST",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                List.of(e.getMessage()),
                "FORBIDDEN",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
