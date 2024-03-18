package pl.kurs.figures.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = ShapeParameterValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidShapeParameters {
    String message() default "Invalid number of parameters for shape type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

