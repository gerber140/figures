package pl.kurs.figures.service;

import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.*;


public class ShapeFactory {
    public static Shape createShape(CreateShapeCommand command) {
        return switch (command.getType()) {
            case CIRCLE -> {
                 yield new Circle(command.getParameters().get(0));
            }
            case RECTANGLE -> {
                yield new Rectangle(command.getParameters().get(0), command.getParameters().get(1));
            }
            case SQUARE -> {
                yield new Square(command.getParameters().get(0));
            }
            default -> throw new InvalidShapeException("Unsupported shape type: " + command.getType());
        };
    }
}
