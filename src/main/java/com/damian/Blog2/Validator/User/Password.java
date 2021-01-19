package com.damian.Blog2.Validator.User;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "Your password is incorrect! It need at least one lowercase latter, one uppercase letter, one special character and it's length is at least 8 characters long!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}