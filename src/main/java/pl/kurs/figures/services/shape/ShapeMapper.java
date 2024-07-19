package pl.kurs.figures.services.shape;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.kurs.figures.services.change.FieldChange;
import pl.kurs.figures.services.change.ShapeChangeManager;
import pl.kurs.figures.command.*;
import pl.kurs.figures.dto.CircleDTO;
import pl.kurs.figures.dto.RectangleDTO;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.exceptions.InvalidShapeUpdateException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ShapeMapper {
    private ModelMapper modelMapper;
    private ShapeChangeManager shapeChangeManager;

    public ShapeDTO toDto(Shape shape) {
        if (shape instanceof Circle) {
            return circleToDto(shape);
        } else if (shape instanceof Square) {
            return squareToDto(shape);
        } else if (shape instanceof Rectangle) {
            return rectangleToDto(shape);
        } else {
            throw new InvalidShapeException("Unsupported shape type");
        }
    }

    private CircleDTO circleToDto(Shape shape) {
        CircleDTO circleDTO = modelMapper.map(shape, CircleDTO.class);
        circleDTO.setType(Type.CIRCLE);
        return circleDTO;
    }

    private SquareDTO squareToDto(Shape shape) {
        SquareDTO squareDTO = modelMapper.map(shape, SquareDTO.class);
        squareDTO.setType(Type.SQUARE);
        return squareDTO;
    }

    private RectangleDTO rectangleToDto(Shape shape) {
        RectangleDTO rectangleDTO = modelMapper.map(shape, RectangleDTO.class);
        rectangleDTO.setType(Type.RECTANGLE);
        return rectangleDTO;
    }

    public Shape commandToShape(Shape shape, UpdateShapeCommand command, String username) {
        List<FieldChange> fieldChanges = new ArrayList<>();

        if (shape instanceof Square) {
            if (command.getSide() == null) {
                throw new InvalidShapeUpdateException("Square update requires a 'side' property.");
            }
            fieldChanges.add(new FieldChange("side", ((Square) shape).getSide(), command.getSide()));
            ((Square) shape).setSide(command.getSide());
        } else if (shape instanceof Rectangle) {
            setRectangleProperties(fieldChanges, (Rectangle) shape, command);
        } else if (shape instanceof Circle) {
            if (command.getRadius() == null) {
                throw new InvalidShapeUpdateException("Circle update requires a 'radius' property.");
            }
            fieldChanges.add(new FieldChange("radius", ((Circle) shape).getRadius(), command.getRadius()));
            ((Circle) shape).setRadius(command.getRadius());
        } else {
            throw new UnsupportedOperationException("Unsupported shape type.");
        }

        boolean ifNoChange = fieldChanges.stream().anyMatch(x -> x.getOldValue() == x.getNewValue());
        if(!ifNoChange) {
            shapeChangeManager.logChanges(
                    shape,
                    fieldChanges,
                    username
            );
        }
        return shape;
    }

    private void setRectangleProperties(List<FieldChange> fieldChanges, Rectangle shape, UpdateShapeCommand command) {

        if (command.getFirstSide() == null && command.getSecondSide() == null) {

            throw new InvalidShapeUpdateException("Rectangle update requires 'firstSide' and 'secondSide' properties.");
        }

        else if (command.getFirstSide() != null && command.getSecondSide() != null) {

            if (!command.getFirstSide().equals(command.getSecondSide())) {
                shape.setFirstSide(command.getFirstSide());
                shape.setSecondSide(command.getSecondSide());

                fieldChanges.add(new FieldChange("firstSide", shape.getFirstSide(), command.getFirstSide()));
                fieldChanges.add(new FieldChange("secondSide", shape.getSecondSide(), command.getSecondSide()));
            } else throw new InvalidShapeUpdateException("The rectangle must have two different sides");

        } else {
            if (command.getFirstSide() != null) {
                if (!command.getFirstSide().equals(shape.getSecondSide())) {

                    fieldChanges.add(new FieldChange("firstSide", shape.getFirstSide(), command.getFirstSide()));
                    shape.setFirstSide(command.getFirstSide());

                } else throw new InvalidShapeUpdateException("The rectangle must have two different sides");
            }
            if (command.getSecondSide() != null) {
                if (!command.getSecondSide().equals(shape.getFirstSide())) {

                    fieldChanges.add(new FieldChange("secondSide", shape.getSecondSide(), command.getSecondSide()));
                    shape.setSecondSide(command.getSecondSide());

                } else throw new InvalidShapeUpdateException("The rectangle must have two different sides");
            }
        }
    }

}
