package pl.kurs.figures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.figures.model.ShapeChange;

public interface ShapeChangeRepository extends JpaRepository<ShapeChange, Long> {
}
