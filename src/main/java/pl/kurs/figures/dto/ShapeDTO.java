package pl.kurs.figures.dto;

import lombok.Data;
import pl.kurs.figures.model.Type;
import java.time.LocalDate;

@Data
public abstract class ShapeDTO {
    private Long id;
    private Type type;
    private double area;
    private double perimeter;
    private int version;
    private String createdBy;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;
    private String lastModifiedBy;
}
