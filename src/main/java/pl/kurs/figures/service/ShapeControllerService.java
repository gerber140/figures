package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.CircleDTO;
import pl.kurs.figures.dto.RectangleDTO;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.model.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ShapeControllerService {
    private ShapeService shapeService;
    private ModelMapper modelMapper;
    public ShapeDTO addShape(CreateShapeCommand command){
        Shape shape = ShapeFactory.createShape(command);
        shapeService.addShape(shape);
        return getShapeDto(shape);
    }

    public List<ShapeDTO> getShapes() {
        return shapeService.getAll()
                .stream()
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
            throw new IllegalArgumentException("Unsupported shape type");
        }
    }
}
