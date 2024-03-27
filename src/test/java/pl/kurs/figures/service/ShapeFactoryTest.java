package pl.kurs.figures.service;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ShapeFactoryTest {

    @Test
    void createCircleWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.CIRCLE, List.of(5.0));

        Shape shape = ShapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Circle.class, shape);
        assertEquals(5.0, ((Circle) shape).getRadius());
    }

    @Test
    void createRectangleWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.RECTANGLE, List.of(4.0, 6.0));

        Shape shape = ShapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Rectangle.class, shape);
        assertEquals(4.0, ((Rectangle) shape).getFirstSide());
        assertEquals(6.0, ((Rectangle) shape).getSecondSide());
    }

    @Test
    void createSquareWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(7.0));

        Shape shape = ShapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Square.class, shape);
        assertEquals(7.0, ((Square) shape).getSide());
    }

}