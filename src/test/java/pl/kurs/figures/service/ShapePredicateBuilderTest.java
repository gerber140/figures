package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShapePredicateBuilderTest {

    @InjectMocks
    private ShapePredicateBuilder shapePredicateBuilder;

    private ShapeSearchCriteria criteria;

    @BeforeEach
    void setUp() {
        criteria = new ShapeSearchCriteria();
    }

    @Test
    void shouldBuildPredicateForCreatedBy() {
        criteria.setCreatedBy("user");

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
    }
    @Test
    void shouldBuildPredicateForSetType() {
        criteria.setType("square");

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
    }


}