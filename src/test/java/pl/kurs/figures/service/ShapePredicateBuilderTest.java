package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.figures.criteria.ShapeSearchCriteria;

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
        criteria.setCreatedBy("testUser1");

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
    }
    @Test
    void shouldBuildPredicateForSetType() {
        criteria.setType("square");
        criteria.setSideFrom(10.0);

        BooleanBuilder builder = shapePredicateBuilder.buildPredicate(criteria);

        assertTrue(builder.hasValue());
    }

}