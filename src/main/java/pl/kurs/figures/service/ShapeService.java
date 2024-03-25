package pl.kurs.figures.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.model.*;
import pl.kurs.figures.repository.ShapeRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class ShapeService {
    private ShapeRepository shapeRepository;
    private ShapePredicateBuilder shapePredicate;

    public Shape addShape(Shape shape) {
        shape = Optional.ofNullable(shape)
                .filter(x -> Objects.isNull(x.getId()))
                .orElseThrow(() -> new InvalidEntityException("Invalid entity for save"));
        return shapeRepository.save(shape);
    }

    public List<Shape> getShapes(ShapeSearchCriteria criteria, int page, int size) {
        BooleanBuilder builder = shapePredicate.buildPredicate(criteria);
        List<Shape> shapeList = new ArrayList<>();

        Iterable<Shape> shapes = shapeRepository.findAll(builder, PageRequest.of(page, size));
        shapes.forEach(shapeList::add);

        return shapeList;
    }
}
