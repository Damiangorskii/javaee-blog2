package com.damian.Blog2.Validator.Post;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TagsValidator.class)
public @interface Tags {

    String message() default "Tags are not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}