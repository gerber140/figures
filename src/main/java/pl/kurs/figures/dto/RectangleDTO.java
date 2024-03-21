package pl.kurs.figures.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RectangleDTO extends ShapeDTO {
    private double firstSide;
    private double SecondSide;
}
