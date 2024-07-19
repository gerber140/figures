package pl.kurs.figures.services.shape;

import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Component;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.model.*;

import java.time.LocalTime;


@Component
public class ShapePredicateBuilder {

    public BooleanBuilder buildPredicate(ShapeSearchCriteria criteria) {
        BooleanBuilder builder = new BooleanBuilder();

        if (criteria.getCreatedBy() != null) {
            builder.and(QShape.shape.createdBy.eq(criteria.getCreatedBy()));
        }
        if (criteria.getType() != null) {
            builder.and(QShape.shape.type.eq(criteria.getType()));
            if ("SQUARE".equals(criteria.getType())) {
                if (criteria.getSideFrom() != null) {
                    builder.and(QShape.shape.as(QSquare.class).side.goe(criteria.getSideFrom()));
                }
                if (criteria.getSideTo() != null) {
                    builder.and(QShape.shape.as(QSquare.class).side.loe(criteria.getSideTo()));
                }
            }
            if ("CIRCLE".equals(criteria.getType())) {
                if (criteria.getRadiusFrom() != null) {
                    builder.and(QShape.shape.as(QCircle.class).radius.goe(criteria.getRadiusFrom()));
                }
                if (criteria.getRadiusTo() != null) {
                    builder.and(QShape.shape.as(QCircle.class).radius.loe(criteria.getRadiusTo()));
                }
            }
            if ("RECTANGLE".equals(criteria.getType())) {
                if (criteria.getFirstSideFrom() != null) {
                    builder.and(QShape.shape.as(QRectangle.class).firstSide.goe(criteria.getFirstSideFrom()));
                }
                if (criteria.getFirstSideTo() != null) {
                    builder.and(QShape.shape.as(QRectangle.class).firstSide.loe(criteria.getFirstSideTo()));
                }
                if (criteria.getSecondSideFrom() != null) {
                    builder.and(QShape.shape.as(QRectangle.class).secondSide.goe(criteria.getSecondSideFrom()));
                }
                if (criteria.getSecondSideTo() != null) {
                    builder.and(QShape.shape.as(QRectangle.class).secondSide.loe(criteria.getSecondSideTo()));
                }
            }
        }
        if (criteria.getAreaFrom() != null) {
            builder.and(QShape.shape.area.goe(criteria.getAreaFrom()));
        }
        if (criteria.getAreaTo() != null) {
            builder.and(QShape.shape.area.loe(criteria.getAreaTo()));
        }
        if (criteria.getPerimeterFrom() != null) {
            builder.and(QShape.shape.perimeter.goe(criteria.getPerimeterFrom()));
        }
        if (criteria.getPerimeterTo() != null) {
            builder.and(QShape.shape.perimeter.loe(criteria.getPerimeterTo()));
        }
        if (criteria.getCreatedAtFrom() != null) {
            builder.and(QShape.shape.createdAt.goe(criteria.getCreatedAtFrom().atStartOfDay()));
        }
        if (criteria.getCreatedAtTo() != null) {
            builder.and(QShape.shape.createdAt.loe(criteria.getCreatedAtTo().atTime(LocalTime.MAX)));
        }
        if (criteria.getVersionFrom() != null) {
            builder.and(QShape.shape.version.goe(criteria.getVersionFrom()));
        }
        if (criteria.getVersionTo() != null) {
            builder.and(QShape.shape.version.loe(criteria.getVersionTo()));
        }

        return builder;
    }
}
