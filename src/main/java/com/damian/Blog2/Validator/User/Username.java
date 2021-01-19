package com.damian.Blog2.Validator.User;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {

    String message() default "Username is probably already taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
