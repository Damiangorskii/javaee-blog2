package com.damian.Blog2.Validator.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<Username, String> {


    public void initialize(Username constraint) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        final String regex = "^[[:alnum:]]+([[:alnum:]]+)*$";

        return username.length()>=2 && username.length()<=50 && username.matches(regex);

    }

}
