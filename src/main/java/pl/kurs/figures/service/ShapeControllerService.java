package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.model.*;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;
import pl.kurs.figures.security.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ShapeDTO> getShapes(ShapeSearchCriteria criteria, int page, int size) {
        List<Shape> shapes;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            shapes = shapeService.getShapes(criteria, page, size);
        } else {
            shapes = shapeService.getShapesForCurrentUser(criteria, page, size);
        }
        return shapes.stream()
                .map(shapeMapper::toDto)
                .collect(Collectors.toList());
    }
}
