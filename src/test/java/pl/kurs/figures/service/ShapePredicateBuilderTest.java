package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.services.shape.ShapePredicateBuilder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
class ShapePredicateBuilderTest {
    @Mock
    private ShapeSearchCriteria criteria;
    @InjectMocks
    private ShapePredicateBuilder shapePredicateBuilder;

    @BeforeEach
    void setUp() {
        criteria = new ShapeSearchCriteria();
    }

    @Test
    void shouldReturnFalseWhenNoCriteria() {
        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);
        assertFalse(builder.hasValue());
    }

    @Test
    void shouldBuildPredicateForCreatedBy() {
        criteria.setCreatedBy("user");

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.createdBy = user"));
    }

    @Test
    void shouldBuildPredicateForSquareSide() {
        criteria.setType("SQUARE");
        criteria.setSideFrom(5.0);
        criteria.setSideTo(10.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.side >= 5.0"));
        assertTrue(builder.getValue().toString().contains("shape.side <= 10.0"));
    }

    @Test
    void shouldBuildPredicateForCircleRadius() {
        criteria.setType("CIRCLE");
        criteria.setRadiusFrom(5.0);
        criteria.setRadiusTo(10.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.radius >= 5.0"));
        assertTrue(builder.getValue().toString().contains("shape.radius <= 10.0"));
    }

    @Test
    void shouldBuildPredicateForRectangleSides() {
        criteria.setType("RECTANGLE");
        criteria.setFirstSideFrom(5.0);
        criteria.setFirstSideTo(10.0);
        criteria.setSecondSideFrom(3.0);
        criteria.setSecondSideTo(8.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.firstSide >= 5.0"));
        assertTrue(builder.getValue().toString().contains("shape.firstSide <= 10.0"));
        assertTrue(builder.getValue().toString().contains("shape.secondSide >= 3.0"));
        assertTrue(builder.getValue().toString().contains("shape.secondSide <= 8.0"));
    }

    @Test
    void shouldBuildPredicateForArea() {
        criteria.setAreaFrom(50.0);
        criteria.setAreaTo(100.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.area >= 50.0"));
        assertTrue(builder.getValue().toString().contains("shape.area <= 100.0"));
    }

    @Test
    void shouldBuildPredicateForPerimeter() {
        criteria.setPerimeterFrom(20.0);
        criteria.setPerimeterTo(50.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.perimeter >= 20.0"));
        assertTrue(builder.getValue().toString().contains("shape.perimeter <= 50.0"));
    }

    @Test
    void shouldBuildPredicateForCreatedAt() {
        criteria.setCreatedAtFrom(LocalDate.of(2020, 1, 1));
        criteria.setCreatedAtTo(LocalDate.of(2020, 12, 31));

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.createdAt >= 2020-01-01T00:00"));
        assertTrue(builder.getValue().toString().contains("shape.createdAt <= 2020-12-31T23:59"));
    }

    @Test
    void shouldBuildPredicateForVersion() {
        criteria.setVersionFrom(1);
        criteria.setVersionTo(5);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
        assertTrue(builder.getValue().toString().contains("shape.version >= 1"));
        assertTrue(builder.getValue().toString().contains("shape.version <= 5"));
    }

}