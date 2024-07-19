package pl.kurs.figures.dto;

import jakarta.persistence.*;
import lombok.Data;
import pl.kurs.figures.model.Shape;

import java.time.LocalDateTime;

@Data
public class ShapeChangeDTO {
    private LocalDateTime changeTime;
    private long shapeId;
    private String changedField;
    private double oldValue;
    private double newValue;
    private String modifiedBy;
}
