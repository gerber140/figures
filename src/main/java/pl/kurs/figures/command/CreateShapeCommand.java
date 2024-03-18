package pl.kurs.figures.command;

import lombok.Data;
import pl.kurs.figures.model.Type;
import pl.kurs.figures.validations.ValidShapeParameters;

import java.util.List;

@Data
@ValidShapeParameters
public class CreateShapeCommand {
    private Type type;
    private List<Double> parameters;
}
