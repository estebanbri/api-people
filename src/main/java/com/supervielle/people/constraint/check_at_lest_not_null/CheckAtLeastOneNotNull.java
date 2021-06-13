package com.supervielle.people.constraint.check_at_lest_not_null;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = CheckAtLeastOneNotNullValidator.class)
public @interface CheckAtLeastOneNotNull {
    String message() default "Invalid length of contact data requires al least one";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
