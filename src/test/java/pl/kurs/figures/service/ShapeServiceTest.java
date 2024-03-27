package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.repository.ShapeRepository;
import pl.kurs.figures.security.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShapeServiceTest {
    @Mock
    private ShapeRepository shapeRepository;
    @Mock
    private ShapePredicateBuilder shapePredicateBuilder;
    @InjectMocks
    private ShapeService shapeService;
    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @AfterEach
    public void afterEach() {
        shapeRepository.deleteAll();
    }

    static Stream<Arguments> shapeProvider() {
        return Stream.of(
                Arguments.of(new Square(5)),
                Arguments.of(new Circle(5)),
                Arguments.of(new Rectangle(5, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("shapeProvider")
    void shouldReturnsSavedShapeWhenAddShape(Shape shape) {
        if (shape instanceof Square) {
            ((Square) shape).calculateProperties();
        } else if (shape instanceof Circle) {
            ((Circle) shape).calculateProperties();
        } else if (shape instanceof Rectangle) {
            ((Rectangle) shape).calculateProperties();
        }

        when(shapeRepository.save(shape)).thenReturn(shape);

        Shape returnedShape = shapeService.addShape(shape);

        assertNotNull(returnedShape);

        if (shape instanceof Square) {
            assertEquals(25, returnedShape.getArea());
            assertEquals(20, returnedShape.getPerimeter());
        } else if (shape instanceof Circle) {
            assertEquals( Math.PI * 25, returnedShape.getArea());
            assertEquals(Math.PI * 10, returnedShape.getPerimeter());
        } else if (shape instanceof Rectangle) {
            assertEquals(20, returnedShape.getArea());
            assertEquals(18, returnedShape.getPerimeter());
        }
    }
    @Test
    void shouldReturnInvalidEntityExceptionWhenAddShapeWhileShapeIsNull(){
        assertThrows(InvalidEntityException.class, () -> shapeService.addShape(null));
    }
    @Test
    void shouldReturnInvalidEntityExceptionWhenAddShapeWhileShapeIdIsNotNull(){
        Square square = new Square(6);
        square.setId(1L);

        assertThrows(InvalidEntityException.class, () -> shapeService.addShape(square));
    }

    @Test
    void shouldReturnShapesListWhenGetShapes() {
        ShapeSearchCriteria criteria = new ShapeSearchCriteria();
        int page = 0;
        int size = 10;
        BooleanBuilder builder = new BooleanBuilder();

        List<Shape> expectedShapesList = List.of(new Square(5), new Circle(3), new Rectangle(5,4), new Square(6), new Circle(4), new Rectangle(7,8));
        Page<Shape> pagedShapes = new PageImpl<>(expectedShapesList);

        when(shapePredicateBuilder.buildPredicate(criteria)).thenReturn(builder);
        when(shapeRepository.findAll(builder, PageRequest.of(page, size))).thenReturn(pagedShapes);

        List<Shape> resultShapes = shapeService.getShapes(criteria, page, size);

        assertNotNull(resultShapes);
        assertEquals(expectedShapesList.size(), resultShapes.size());
        assertTrue(resultShapes.containsAll(expectedShapesList));
    }

    @Test
    public void shouldGetShapesForCurrentUser() {
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        String currentUser = "testUser";

        ShapeSearchCriteria criteria = new ShapeSearchCriteria();
        criteria.setCreatedBy(currentUser);

        int page = 0;
        int size = 10;

        BooleanBuilder builder = new BooleanBuilder();

        List<Shape> expectedShapesList = List.of(new Square(5), new Circle(3), new Rectangle(5,4), new Square(6), new Circle(4), new Rectangle(7,8));
        Page<Shape> pagedShapes = new PageImpl<>(expectedShapesList);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(currentUser);
        when(shapePredicateBuilder.buildPredicate(criteria)).thenReturn(builder);
        when(shapeRepository.findAll(builder, PageRequest.of(page, size))).thenReturn(pagedShapes);

        List<Shape> resultShapes = shapeService.getShapesForCurrentUser(criteria, page, size);

        assertNotNull(resultShapes);
        assertEquals(expectedShapesList.size(), resultShapes.size());
        assertTrue(resultShapes.containsAll(expectedShapesList));

    }




}