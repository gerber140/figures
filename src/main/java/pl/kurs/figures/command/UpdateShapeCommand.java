package pl.kurs.figures.command;

import lombok.Data;

@Data
public class UpdateShapeCommand {
    private Double side;
    private Double radius;
    private Double firstSide;
    private Double secondSide;
}
