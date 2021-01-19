package com.damian.Blog2.Validator.Post;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostContentValidator implements ConstraintValidator<PostContent, String> {

    public void initialize(PostContent constraint) {
    }

    @Override
    public boolean isValid(String postContent, ConstraintValidatorContext constraintValidatorContext) {


        return postContent.length()>=2 && postContent.length()<=1000;

    }

}
