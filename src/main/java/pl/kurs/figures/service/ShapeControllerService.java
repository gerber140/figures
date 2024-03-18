package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.CircleDTO;
import pl.kurs.figures.dto.RectangleDTO;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.model.*;

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

    private ShapeDTO getShapeDto(Shape shape) {
        if (shape instanceof Circle) {
            CircleDTO circleDTO = modelMapper.map(shape, CircleDTO.class);
            circleDTO.setType(Type.CIRCLE);
            circleDTO.setArea();
            return ;
        } else if (shape instanceof Square) {
            return modelMapper.map(shape, SquareDTO.class);
        } else if (shape instanceof Rectangle) {
            return modelMapper.map(shape, RectangleDTO.class);
        } else {
            throw new IllegalArgumentException("Unsupported shape type");
        }
    }
}
