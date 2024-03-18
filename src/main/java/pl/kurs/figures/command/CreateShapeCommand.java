package pl.kurs.figures.command;

import lombok.Data;
import pl.kurs.figures.model.Type;
import java.util.List;

@Data
public class CreateShapeCommand {
    private Type type;
    private List<Double> parameters;
}
