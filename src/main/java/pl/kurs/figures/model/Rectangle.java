package pl.kurs.figures.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Rectangles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rectangle extends Shape {
    private double width;
    private double height;

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * width + 2 * height;
    }
}
