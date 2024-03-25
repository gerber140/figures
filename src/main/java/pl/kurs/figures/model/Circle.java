package pl.kurs.figures.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("CIRCLE")
public class Circle extends Shape {
    private double radius;

    @Override
    @PrePersist
    @PreUpdate
    public void calculateProperties() {
        this.setArea(Math.PI * radius * radius);
        this.setPerimeter(2 * Math.PI * radius);
    }
}
