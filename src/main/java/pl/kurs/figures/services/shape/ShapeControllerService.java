package pl.kurs.figures.services.shape;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.command.UpdateShapeCommand;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.model.*;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.service.AuthenticationService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ShapeControllerService {
    private ShapeService shapeService;
    private ShapeMapper shapeMapper;
    private AuthenticationService authenticationService;

    public ShapeDTO addShape(CreateShapeCommand command) {
        Shape shape = shapeService.addShape(command);
        return shapeMapper.toDto(shape);
    }

    public List<ShapeDTO> getShapes(ShapeSearchCriteria criteria, int page, int size) {
        List<Shape> shapes;

        User user = authenticationService.getAuthenticatedUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            shapes = shapeService.getShapes(criteria, page, size);
        } else {
            shapes = shapeService.getShapesForCurrentUser(criteria, page, size, user);
        }
        return shapes.stream()
                .map(shapeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ShapeDTO updateShape(Long id, UpdateShapeCommand command) throws AccessDeniedException {
        Shape existingShape = shapeService.getShapeById(id);

        User currentUser = authenticationService.getAuthenticatedUser();

        if(currentUser.getUsername().equals(existingShape.getCreatedBy()) || currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            Shape shape = shapeMapper.commandToShape(existingShape, command, currentUser.getUsername());
            Shape editedShape = shapeService.editShape(shape);
            return shapeMapper.toDto(editedShape);
        } else {
            throw new AccessDeniedException("You do not have permission to update this shape.");
        }
    }

}
