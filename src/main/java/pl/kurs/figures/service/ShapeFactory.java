package pl.kurs.figures.service;

import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.*;

import java.time.LocalDate;

public class ShapeFactory {
    public static Shape createShape(CreateShapeCommand command) {
        return switch (command.getType()) {
            case CIRCLE -> {
                Circle circle = new Circle(command.getParameters().get(0));
                yield setShapeFields(circle, command);
            }
            case RECTANGLE -> {
                Rectangle rectangle = new Rectangle(command.getParameters().get(0), command.getParameters().get(1));
                yield setShapeFields(rectangle, command);
            }
            case SQUARE -> {
                Square square = new Square(command.getParameters().get(0));
                yield setShapeFields(square, command);
            }
            default -> throw new InvalidShapeException("Unsupported shape type: " + command.getType());
        };
    }

    private static Shape setShapeFields(Shape shape, CreateShapeCommand command) {
        shape.setVersion(1);
        shape.setCreatedBy(command.getCreatedBy());
        shape.setCreatedAt(LocalDate.now());
        shape.setLastModifiedAt(LocalDate.now());
        shape.setLastModifiedBy(command.getCreatedBy());
        return shape;
    }
}
