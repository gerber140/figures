package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.figures.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.dto.CircleDTO;
import pl.kurs.figures.dto.RectangleDTO;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.*;

import java.util.List;

import static pl.kurs.figures.service.ShapeFactory.createShape;

@Service
@AllArgsConstructor
public class ShapeControllerService {
    private ShapeService shapeService;
    private ModelMapper modelMapper;

    public ShapeDTO addShape(CreateShapeCommand command) {
        Shape shape = shapeService.addShape(createShape(command));
        return getShapeDto(shape);
    }

    public List<ShapeDTO> getShapes(ShapeSearchCriteria criteria) {
        List<Shape> shapes = shapeService.getShapes(criteria);
        return shapes.stream()
                .map(this::getShapeDto)
                .toList();
    }

    private ShapeDTO getShapeDto(Shape shape) {
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
