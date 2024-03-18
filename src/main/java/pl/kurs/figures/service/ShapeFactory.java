package pl.kurs.figures.service;

import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.exceptions.InvalidShapeTypeException;
import pl.kurs.figures.model.*;

public class ShapeFactory {
    public static Shape createShape(CreateShapeCommand command){
        return switch (command.getType()) {
            case SQUARE -> new Square(command.getParameters().get(0));
            case CIRCLE -> new Circle(command.getParameters().get(0));
            case RECTANGLE -> new Rectangle(command.getParameters().get(0), command.getParameters().get(1));
            default -> throw new InvalidShapeTypeException("Unsupported shape type");
        };
    }
}
