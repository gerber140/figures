package pl.kurs.figures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.kurs.figures.command.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public abstract class ShapeDTO {
    private Long id;
    private Type type;
    private double area;
    private double perimeter;
    private int version;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
}
