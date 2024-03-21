package pl.kurs.figures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShapeSearchCriteria {
    private String createdBy;

    private String type;

    private Double areaFrom;
    private Double areaTo;

    private Double perimeterFrom;
    private Double perimeterTo;

    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;

    private Double sideFrom;
    private Double sideTo;

    private Double radiusFrom;
    private Double radiusTo;

    private Double firstSideFrom;
    private Double firstSideTo;
    private Double secondSideFrom;
    private Double secondSideTo;
}
