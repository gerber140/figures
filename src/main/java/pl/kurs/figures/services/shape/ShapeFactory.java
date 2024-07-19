package pl.kurs.figures.services.shape;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.*;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.service.AuthenticationService;

@Component
@AllArgsConstructor
public class ShapeFactory {
    private AuthenticationService authenticationService;

    public Shape createShape(CreateShapeCommand command) {
        User currentUser = authenticationService.getAuthenticatedUser();
        Shape shape = switch (command.getType()) {
            case CIRCLE -> {
                yield new Circle(command.getParameters().get(0));
            }
            case RECTANGLE -> {
                yield new Rectangle(command.getParameters().get(0), command.getParameters().get(1));
            }
            case SQUARE -> {
                yield new Square(command.getParameters().get(0));
            }
        };
        shape.setUser(currentUser);
        return shape;
    }
}
