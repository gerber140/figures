package pl.kurs.figures.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Squares")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Square extends Shape {
    private double width;
    private final Type type = Type.SQUARE;
    @Override
    public double calculateArea() {
        return width * width;
    }

    @Override
    public double calculatePerimeter() {
        return width * 4;
    }
}
