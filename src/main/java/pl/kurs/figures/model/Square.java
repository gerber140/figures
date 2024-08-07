package pl.kurs.figures.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SQUARE")
public class Square extends Shape{
    private double side;

    @Override
    @PrePersist
    @PreUpdate
    public void calculateProperties() {
        this.setPerimeter(side*4);
        this.setArea(side*side);
    }

}
