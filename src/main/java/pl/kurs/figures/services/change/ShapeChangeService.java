package pl.kurs.figures.services.change;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.repository.ShapeChangeRepository;
import pl.kurs.figures.model.ShapeChange;
import pl.kurs.figures.exceptions.InvalidIdException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShapeChangeService {
    private ShapeChangeRepository shapeChangeRepository;

    public ShapeChange getChangeById(Long id) {
        return shapeChangeRepository.findById(Optional.ofNullable(id)
                        .filter(x -> x > 0)
                        .orElseThrow(() -> new InvalidIdException("Invalid id:" + id)))
                .orElseThrow(() -> new EntityNotFoundException("Entity with id not found: " + id));
    }
}
