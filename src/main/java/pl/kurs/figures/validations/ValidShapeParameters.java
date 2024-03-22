package pl.kurs.figures.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ShapeParametersValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface ValidShapeParameters {
    String message() default "Invalid shape parameters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

