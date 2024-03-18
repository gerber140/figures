package pl.kurs.figures.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double area;
    private double perimeter;
    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    @PostLoad
    @PostPersist
    private void calculateFields(){
        area = calculateArea();
        perimeter = calculatePerimeter();
    }
}
