package com.damian.Blog2.Validator.Comment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CommentContentValidator.class)
public @interface CommentContent {

    String message() default "Content is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}