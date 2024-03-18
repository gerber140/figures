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

        switch (command.getType()) {
            case SQUARE:
            case CIRCLE:
                return command.getParameters().size() == 1;
            case RECTANGLE:
                return command.getParameters().size() == 2;
            default:
                return false;
        }
    }
}
