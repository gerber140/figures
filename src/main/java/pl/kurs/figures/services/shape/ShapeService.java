package pl.kurs.figures.services.shape;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.exceptions.InvalidIdException;
import pl.kurs.figures.model.*;
import pl.kurs.figures.repository.ShapeRepository;
import pl.kurs.figures.security.service.AuthenticationService;

import java.util.*;

@Service
@AllArgsConstructor
public class ShapeService {
    private ShapeRepository shapeRepository;
    private ShapePredicateBuilder shapePredicate;
    private ShapeFactory shapeFactory;

    public Shape addShape(CreateShapeCommand command) {
        Shape shape = shapeFactory.createShape(command);
        return shapeRepository.save(shape);
    }

    public List<Shape> getShapes(ShapeSearchCriteria criteria, int page, int size) {
        return getShapeList(criteria, page, size);
    }

    public List<Shape> getShapesForCurrentUser(ShapeSearchCriteria criteria, int page, int size, UserDetails user) {
        criteria.setCreatedBy(user.getUsername());
        return getShapeList(criteria, page, size);
    }

    private List<Shape> getShapeList(ShapeSearchCriteria criteria, int page, int size) {
        BooleanBuilder builder = shapePredicate.buildPredicate(criteria);
        List<Shape> shapeList = new ArrayList<>();

        Iterable<Shape> shapes = shapeRepository.findAll(builder, PageRequest.of(page, size));
        shapes.forEach(shapeList::add);
        return shapeList;
    }

    public Shape editShape(Shape shape) {
        shape = Optional.ofNullable(shape)
                .filter(x -> Objects.nonNull(x.getId()))
                .orElseThrow(() -> new InvalidEntityException("Invalid entity for update"));
        return shapeRepository.save(shape);
    }

    public Shape getShapeById(Long id) {
        return shapeRepository.findById(Optional.ofNullable(id)
                        .filter(x -> x > 0)
                        .orElseThrow(() -> new InvalidIdException("Invalid id:" + id)))
                .orElseThrow(() -> new EntityNotFoundException("Entity with id not found: " + id));
    }
}





































