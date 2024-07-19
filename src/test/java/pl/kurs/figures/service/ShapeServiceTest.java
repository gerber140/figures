package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.exceptions.InvalidIdException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.repository.ShapeRepository;
import pl.kurs.figures.services.shape.ShapeFactory;
import pl.kurs.figures.services.shape.ShapePredicateBuilder;
import pl.kurs.figures.services.shape.ShapeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShapeServiceTest {
    @Mock
    private ShapeRepository shapeRepository;
    @Mock
    private ShapePredicateBuilder shapePredicateBuilder;
    @Mock
    private ShapeFactory shapeFactory;
    @InjectMocks
    private ShapeService shapeService;
    private ShapeSearchCriteria criteria;
    private BooleanBuilder booleanBuilder;
    private List<Shape> shapeList;


    @AfterEach
    public void afterEach() {
        shapeRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        criteria = new ShapeSearchCriteria();
        booleanBuilder = new BooleanBuilder();
        shapeList = new ArrayList<>();
    }

    static Stream<Arguments> shapeProvider() {
        return Stream.of(
                Arguments.of(new CreateShapeCommand(Type.SQUARE, List.of(5.0)), new Square(5)),
                Arguments.of(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)), new Circle(5)),
                Arguments.of(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 4.0)), new Rectangle(5, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("shapeProvider")
    void shouldReturnSavedShapeWhenAddShape(CreateShapeCommand command, Shape shape) {

        if (shape instanceof Square) {
            ((Square) shape).calculateProperties();
        } else if (shape instanceof Circle) {
            ((Circle) shape).calculateProperties();
        } else if (shape instanceof Rectangle) {
            ((Rectangle) shape).calculateProperties();
        }

        when(shapeFactory.createShape(command)).thenReturn(shape);
        when(shapeRepository.save(shape)).thenReturn(shape);

        Shape returnedShape = shapeService.addShape(command);

        assertNotNull(returnedShape);
        verify(shapeFactory).createShape(command);
        verify(shapeRepository).save(shape);
        assertEquals(shape, returnedShape);

        if (returnedShape instanceof Square) {
            assertEquals(25, returnedShape.getArea());
            assertEquals(20, returnedShape.getPerimeter());
        } else if (returnedShape instanceof Circle) {
            assertEquals( Math.PI * 25, returnedShape.getArea());
            assertEquals(Math.PI * 10, returnedShape.getPerimeter());
        } else if (returnedShape instanceof Rectangle) {
            assertEquals(20, returnedShape.getArea());
            assertEquals(18, returnedShape.getPerimeter());
        }
    }

    @Test
    void shouldReturnListOfShapes() {
        Square square = new Square(5);
        square.calculateProperties();
        shapeList.add(square);

        Circle circle = new Circle(5);
        circle.calculateProperties();
        shapeList.add(circle);

        Rectangle rectangle = new Rectangle(5, 4);
        rectangle.calculateProperties();
        shapeList.add(rectangle);

        when(shapePredicateBuilder.buildPredicate(criteria)).thenReturn(booleanBuilder);
        when(shapeRepository.findAll(any(BooleanBuilder.class), any(PageRequest.class))).thenReturn(new PageImpl<>(shapeList));

        List<Shape> result = shapeService.getShapes(criteria, 0, 10);

        verify(shapePredicateBuilder).buildPredicate(criteria);
        verify(shapeRepository).findAll(booleanBuilder, PageRequest.of(0, 10));
        assertEquals(shapeList, result);
    }

    @Test
    void shouldReturnEditedShape() {
        Square square = new Square(5);
        square.setId(1L);

        when(shapeRepository.save(square)).thenReturn(square);

        Shape result = shapeService.editShape(square);

        verify(shapeRepository).save(square);
        assertEquals(square, result);
    }

    @Test
    void shouldThrowInvalidEntityExceptionWhileEditShape() {
        Square square = new Square(5);
        assertThrows(InvalidEntityException.class, () -> shapeService.editShape(square));
    }

    @Test
    void shouldReturnShapeById() {
        Square square = new Square(5);
        square.setId(1L);

        when(shapeRepository.findById(1L)).thenReturn(Optional.of(square));

        Shape result = shapeService.getShapeById(1L);

        assertEquals(square, result);
    }

    @Test
    void shouldThrowInvalidIdExceptionWhileGetShapeById() {
        assertThrows(InvalidIdException.class, () -> shapeService.getShapeById(null));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhileGetShapeById() {
        assertThrows(EntityNotFoundException.class, () -> shapeService.getShapeById(100L));
    }
}