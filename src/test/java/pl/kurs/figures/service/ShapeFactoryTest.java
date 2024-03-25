package pl.kurs.figures.service;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ShapeFactoryTest {

    static Stream<Arguments> shapeProvider() {
        return Stream.of(
                Arguments.of(new CreateShapeCommand(Type.SQUARE, List.of(7.0))),
                Arguments.of(new CreateShapeCommand(Type.RECTANGLE, List.of(2.0,4.0))),
                Arguments.of(new CreateShapeCommand(Type.CIRCLE, List.of(6.0)))
        );
    }

    @ParameterizedTest
    @MethodSource("shapeProvider")
    void createShape(CreateShapeCommand command) {

        Shape shape = ShapeFactory.createShape(command);

        if (shape instanceof Square) {
            assertInstanceOf(Square.class, shape);
            assertEquals(7.0, ((Square) shape).getSide());
        } else if (shape instanceof Circle) {
            assertInstanceOf(Circle.class, shape);
            assertEquals(6.0, ((Circle) shape).getRadius());
        } else if (shape instanceof Rectangle) {
            assertInstanceOf(Rectangle.class, shape);
            assertEquals(2.0, ((Rectangle) shape).getFirstSide());
            assertEquals(4.0, ((Rectangle) shape).getSecondSide());
        }
    }
}