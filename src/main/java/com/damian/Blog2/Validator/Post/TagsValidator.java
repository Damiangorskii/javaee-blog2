package com.damian.Blog2.Validator.Post;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagsValidator implements ConstraintValidator<Tags, String> {

    public void initialize(Tags constraint) {
    }

    @Override
    public boolean isValid(String tags, ConstraintValidatorContext constraintValidatorContext) {

        final String regex = "\\w+(?:\\s?\\S?\\w+)*";

        return tags.matches(regex) && tags.length()>=2 && tags.length()<=200;

    }

}
