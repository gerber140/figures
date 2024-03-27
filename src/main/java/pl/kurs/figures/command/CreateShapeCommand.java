package pl.kurs.figures.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.kurs.figures.validations.ValidShapeParameters;
import java.util.List;


@Data
@AllArgsConstructor
@ValidShapeParameters
public class CreateShapeCommand {
    @NotNull
    private Type type;
    @NotEmpty
    private List<Double> parameters;
}
