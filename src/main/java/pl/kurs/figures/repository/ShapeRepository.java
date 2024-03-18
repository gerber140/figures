package pl.kurs.figures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.figures.model.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
}
