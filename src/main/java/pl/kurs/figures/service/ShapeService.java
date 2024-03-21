package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.ShapeSearchCriteria;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.model.*;
import pl.kurs.figures.repository.ShapeRepository;

import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ShapeService {
    private ShapeRepository shapeRepository;

    public Shape addShape(Shape shape) {
        shape = Optional.ofNullable(shape)
                .filter(x -> Objects.isNull(x.getId()))
                .orElseThrow(() -> new InvalidEntityException("Invalid entity for save"));
        return shapeRepository.save(shape);
    }

    public List<Shape> getShapes(ShapeSearchCriteria criteria) {
        BooleanBuilder builder = new BooleanBuilder();

        if (criteria.getCreatedBy() != null) {
            builder.and(QShape.shape.createdBy.eq(criteria.getCreatedBy()));
        }
        if (criteria.getType() != null) {
            builder.and(QShape.shape.type.eq(criteria.getType()));
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
        if ("SQUARE".equals(criteria.getType())) {
            if (criteria.getSideFrom() != null) {
                builder.and(QSquare.square.side.goe(criteria.getSideFrom()));
            }
            if (criteria.getSideTo() != null) {
                builder.and(QSquare.square.side.loe(criteria.getSideTo()));
            }
        }
        if ("CIRCLE".equalsIgnoreCase(criteria.getType())) {
            if (criteria.getRadiusFrom() != null) {
                builder.and(QCircle.circle.radius.goe(criteria.getRadiusFrom()));
            }
            if (criteria.getRadiusTo() != null) {
                builder.and(QCircle.circle.radius.loe(criteria.getRadiusTo()));
            }
        }
        if ("RECTANGLE".equalsIgnoreCase(criteria.getType())) {
            if (criteria.getFirstSideFrom() != null) {
                builder.and(QRectangle.rectangle.firstSide.goe(criteria.getFirstSideFrom()));
            }
            if (criteria.getFirstSideTo() != null) {
                builder.and(QRectangle.rectangle.firstSide.loe(criteria.getFirstSideTo()));
            }
            if (criteria.getSecondSideFrom() != null) {
                builder.and(QRectangle.rectangle.secondSide.goe(criteria.getSecondSideFrom()));
            }
            if (criteria.getSecondSideTo() != null) {
                builder.and(QRectangle.rectangle.secondSide.loe(criteria.getSecondSideTo()));
            }
        }

        Iterable<Shape> shapes = shapeRepository.findAll(builder);
        List<Shape> shapeList = new ArrayList<>();
        shapes.forEach(shapeList::add);
        return shapeList;
    }

}
