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
        this.setPerimeter(Math.PI*radius*2);
        this.setArea(Math.PI*radius*radius);
    }
}
