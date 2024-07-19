package pl.kurs.figures.services.change;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FieldChange {
    private final String fieldName;
    private final double oldValue;
    private final double newValue;
}
