package pl.kurs.figures.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.exceptions.InvalidShapeException;

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
}
