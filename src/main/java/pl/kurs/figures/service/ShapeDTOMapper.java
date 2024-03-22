package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.dto.CircleDTO;
import pl.kurs.figures.dto.RectangleDTO;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;

@Component
@AllArgsConstructor
public class ShapeDTOMapper {
    private ModelMapper modelMapper;

    public ShapeDTO toDto(Shape shape) {
        if (shape instanceof Circle) {
            CircleDTO circleDTO = modelMapper.map(shape, CircleDTO.class);
            circleDTO.setType(Type.CIRCLE);
            return circleDTO;
        } else if (shape instanceof Square) {
            SquareDTO squareDTO = modelMapper.map(shape, SquareDTO.class);
            squareDTO.setType(Type.SQUARE);
            return squareDTO;
        } else if (shape instanceof Rectangle) {
            RectangleDTO rectangleDTO = modelMapper.map(shape, RectangleDTO.class);
            rectangleDTO.setType(Type.RECTANGLE);
            return rectangleDTO;
        } else {
            throw new InvalidShapeException("Unsupported shape type");
        }
    }
}
