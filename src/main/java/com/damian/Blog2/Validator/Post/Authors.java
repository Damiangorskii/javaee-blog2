package com.damian.Blog2.Validator.Post;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuthorsValidator.class)
public @interface Authors {

    String message() default "Authors are not valid! Check if author you added is correct!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
