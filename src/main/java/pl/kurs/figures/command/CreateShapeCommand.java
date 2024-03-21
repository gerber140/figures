package pl.kurs.figures.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.kurs.figures.validations.ValidShapeParameters;
import java.util.List;

@Data
@ValidShapeParameters
public class CreateShapeCommand {
    @NotNull
    private Type type;
    @NotEmpty
    private List<Double> parameters;
}
