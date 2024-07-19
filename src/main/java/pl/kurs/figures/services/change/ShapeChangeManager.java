package pl.kurs.figures.services.change;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.figures.model.ShapeChange;
import pl.kurs.figures.repository.ShapeChangeRepository;
import pl.kurs.figures.model.Shape;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShapeChangeManager {
    private ShapeChangeRepository shapeChangeRepository;

    public void logChanges(Shape shape, List<FieldChange> fieldChanges, String username) {
        List<ShapeChange> changes = new ArrayList<>();
        for (FieldChange fieldChange : fieldChanges) {

            ShapeChange change = new ShapeChange();
            change.setShape(shape);
            change.setChangedField(fieldChange.getFieldName());
            change.setOldValue(fieldChange.getOldValue());
            change.setNewValue(fieldChange.getNewValue());
            change.setChangeTime(LocalDateTime.now());
            change.setModifiedBy(username);
            changes.add(change);

        }
        shapeChangeRepository.saveAll(changes);
    }

}
