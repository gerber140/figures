package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.model.*;
import java.util.List;

import static pl.kurs.figures.service.ShapeFactory.createShape;

@Service
@AllArgsConstructor
public class ShapeControllerService {
    private ShapeService shapeService;
    private ShapeDTOMapper shapeMapper;

    public ShapeDTO addShape(CreateShapeCommand command) {
        Shape shape = shapeService.addShape(createShape(command));
        return shapeMapper.toDto(shape);
    }

    public List<ShapeDTO> getShapes(ShapeSearchCriteria criteria) {
        List<Shape> shapes = shapeService.getShapes(criteria);
        return shapes.stream()
                .map(x -> shapeMapper.toDto(x))
                .toList();
    }
}
