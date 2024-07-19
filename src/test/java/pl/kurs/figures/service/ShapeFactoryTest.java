package pl.kurs.figures.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.exceptions.InvalidShapeException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.service.AuthenticationService;
import pl.kurs.figures.services.shape.ShapeFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShapeFactoryTest {
    @InjectMocks
    private ShapeFactory shapeFactory;
    @Mock
    private AuthenticationService authenticationService;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new User("user", "user", Role.CREATOR);
        when(authenticationService.getAuthenticatedUser()).thenReturn(mockUser);
    }

    @Test
    void createCircleWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.CIRCLE, List.of(5.0));

        Shape shape = shapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Circle.class, shape);
        assertEquals(5.0, ((Circle) shape).getRadius());
        assertEquals(mockUser, shape.getUser());
    }

    @Test
    void createRectangleWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.RECTANGLE, List.of(4.0, 6.0));

        Shape shape = shapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Rectangle.class, shape);
        assertEquals(4.0, ((Rectangle) shape).getFirstSide());
        assertEquals(6.0, ((Rectangle) shape).getSecondSide());
        assertEquals(mockUser, shape.getUser());
    }

    @Test
    void createSquareWithValidParameters() {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(7.0));

        Shape shape = shapeFactory.createShape(command);

        assertNotNull(shape);
        assertInstanceOf(Square.class, shape);
        assertEquals(7.0, ((Square) shape).getSide());
        assertEquals(mockUser, shape.getUser());
    }


}