package pl.kurs.figures.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.kurs.figures.command.CreateShapeCommand;

public class ShapeParametersValidator implements ConstraintValidator<ValidShapeParameters, CreateShapeCommand> {

    @Override
    public boolean isValid(CreateShapeCommand command, ConstraintValidatorContext constraintValidatorContext) {
        if (command == null || command.getType() == null || command.getParameters() == null) {
            return false;
        }

        return switch (command.getType()) {
            case SQUARE, CIRCLE -> command.getParameters().size() == 1;
            case RECTANGLE -> {
                if (command.getParameters().size() != 2) {
                    yield false;
                }
                yield !command.getParameters().get(0).equals(command.getParameters().get(1));
            }
            default -> false;
        };
    }
}
