package pl.kurs.figures.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.exceptions.InvalidEntityException;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.repository.ShapeRepository;

import java.util.Objects;
import java.util.Optional;

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
}
