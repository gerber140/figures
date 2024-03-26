package pl.kurs.figures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.validations.ShapeParametersValidator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShapeParametersValidatorTest {
    private ShapeParametersValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new ShapeParametersValidator();
    }

    @Test
    public void whenCommandIsNull_thenShouldBeInvalid() {
        assertFalse(validator.isValid(null, null));
    }
    @Test
    public void whenTypeIsNull_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(null, List.of(1.0));
        assertFalse(validator.isValid(command, null));
    }

    @Test
    public void whenParametersAreNull_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, null);
        assertFalse(validator.isValid(command, null));
    }

    @Test
    public void whenSquareHasNotExactlyOneParameter_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(1.0, 2.0));
        assertFalse(validator.isValid(command, null));
    }

    @Test
    public void whenCircleHasNotExactlyOneParameter_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.CIRCLE, List.of(2.0, 5.0));
        assertFalse(validator.isValid(command, null));
    }
    @Test
    public void whenRectangleHasNotExactlyTwoParameters_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.RECTANGLE, List.of(3.0, 6.0, 5.0));
        assertFalse(validator.isValid(command, null));
    }

    @Test
    public void whenRectangleHasNotExactlyTwoDifferentParameters_thenShouldBeInvalid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.RECTANGLE, List.of(3.0, 3.0));
        assertFalse(validator.isValid(command, null));
    }

    @Test
    public void whenRectangleHasExactlyTwoDifferentParameters_thenShouldBeValid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.RECTANGLE, List.of(3.0, 4.0));
        assertTrue(validator.isValid(command, null));
    }

    @Test
    public void whenCircleHasExactlyOneParameter_thenShouldBeValid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.CIRCLE, List.of(3.0));
        assertTrue(validator.isValid(command, null));
    }

    @Test
    public void whenSquareHasExactlyOneParameter_thenShouldBeValid() {
        CreateShapeCommand command = new CreateShapeCommand(Type.CIRCLE, List.of(3.0));
        assertTrue(validator.isValid(command, null));
    }
}
