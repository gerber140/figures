package pl.kurs.figures.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.kurs.figures.command.UpdateShapeCommand;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.services.change.ShapeChangeManager;
import pl.kurs.figures.services.shape.ShapeMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ShapeMapperTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ShapeChangeManager shapeChangeManager;
    @InjectMocks
    private ShapeMapper shapeMapper;

    private Square square;
    private Rectangle rectangle;
    private Circle circle;

    @BeforeEach
    void setUp() {
        square = new Square();
        square.setId(1L);
        square.setSide(5.0);

        rectangle = new Rectangle();
        rectangle.setId(2L);
        rectangle.setFirstSide(4.0);
        rectangle.setSecondSide(6.0);

        circle = new Circle();
        circle.setId(3L);
        circle.setRadius(7.0);
    }

    @Test
    void ShouldReturnSquareFromUpdateShapeCommand() {
        UpdateShapeCommand updateShapeCommand = new UpdateShapeCommand();
        updateShapeCommand.setSide(10.0);

        Shape result = shapeMapper.commandToShape(square, updateShapeCommand, "user");

        assertEquals(10.0, ((Square)result).getSide(), 0.0001);
        verify(shapeChangeManager).logChanges(eq(square), anyList(), eq("user"));
    }





}
