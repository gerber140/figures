package pl.kurs.figures.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SquareDTO extends ShapeDTO{
    private double side;
}
