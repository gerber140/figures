package pl.kurs.figures.dto;

import lombok.Data;
import pl.kurs.figures.model.Type;

@Data
public class ShapeDTO {
//    "id","type", "width", "version", "createdBy", "createdAt", "lastModifiedAt", "lastModifiedBy", "area", "perimeter"
    private Long id;
    private Type type;
    private double area;
    private double perimeter;
}
