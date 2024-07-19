package pl.kurs.figures.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.figures.model.Shape;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShapeChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime changeTime;

    @ManyToOne
    @JoinColumn(name = "shape_id")
    private Shape shape;

    private String changedField;
    private double oldValue;
    private double newValue;
    private String modifiedBy;
}
