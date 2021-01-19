package com.damian.Blog2.Validator.User;

import com.damian.Blog2.Models.User;
import com.damian.Blog2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Autowired
    UserRepository userRepository;

    public void initialize(Email constraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        final String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        for (User user: userRepository.findAll()){
            if (email.equals(user.getEmail())){
                return false;
            }
        }

        return email.matches(regex) && email.length()<=100;

    }

}