package pl.kurs.figures.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("RECTANGLE")
public class Rectangle extends Shape{
    private double firstSide;
    private double secondSide;

    @Override
    @PrePersist
    @PreUpdate
    public void calculateProperties() {
        this.setArea(firstSide * secondSide);
        this.setPerimeter(2 * firstSide + 2 * secondSide);
    }
}
