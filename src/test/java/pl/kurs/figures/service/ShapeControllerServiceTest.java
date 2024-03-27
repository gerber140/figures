package pl.kurs.figures.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.kurs.figures.service.ShapeFactory.createShape;

@ExtendWith(MockitoExtension.class)
class ShapeControllerServiceTest {
    @Mock
    private ShapeService shapeService;
    @Mock
    private ShapeDTOMapper shapeMapper;
    @InjectMocks
    private ShapeControllerService shapeControllerService;

    @Test
    void shouldReturnShapeDTOWhenAddShape(){
        // Given
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0));
        Shape shape = new Square(5);
        SquareDTO squareDTO = new SquareDTO();

        when(shapeService.addShape(any(Shape.class))).thenReturn(shape);
        when(shapeMapper.toDto(any(Shape.class))).thenReturn(squareDTO);

        // When
        ShapeDTO result = shapeControllerService.addShape(command);

        // Then
        assertNotNull(result);
        assertInstanceOf(SquareDTO.class, result);
        verify(shapeService).addShape(any(Shape.class));
        verify(shapeMapper).toDto(any(Shape.class));
    }

    @Test
    void shouldGetShapesAndReturnListOfShapeDto() {
        // given
        ShapeSearchCriteria criteria = new ShapeSearchCriteria();
        int page = 0;
        int size = 10;
        List<Shape> shapes = List.of(new Square(), new Square());
        List<ShapeDTO> shapeDTOs = shapes.stream()
                .map(s -> new SquareDTO())
                .collect(Collectors.toList());

        when(shapeService.getShapes(criteria, page, size)).thenReturn(shapes);
        when(shapeMapper.toDto(any(Shape.class))).thenAnswer(invocation -> new SquareDTO());

        // when
        List<ShapeDTO> result = shapeControllerService.getShapes(criteria, page, size);

        // then
        assertNotNull(result);
        assertEquals(shapeDTOs.size(), result.size());
        verify(shapeService).getShapes(criteria, page, size);
        verify(shapeMapper, times(shapes.size())).toDto(any(Shape.class));
    }

}