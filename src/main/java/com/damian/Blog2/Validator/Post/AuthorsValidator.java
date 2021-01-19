package com.damian.Blog2.Validator.Post;

import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuthorsValidator implements ConstraintValidator<Authors, String> {

    @Autowired
    private AuthorRepository authorRepository;

    public void initialize(Authors constraint) {
    }

    @Override
    public boolean isValid(String authors, ConstraintValidatorContext constraintValidatorContext) {

        final String regex = "([A-Z][a-z]+[ ][A-Z][a-z]+[,]?[ ]?)+";
        int counter = 0;
        String[] data = authors.split(", ");
        for (int i=0 ; i< data.length; i++){
            for (Author author: authorRepository.findAll()){
                if (data[i].equals(author.getName())){
                    counter++;
                }
            }
        }
        if (counter == data.length){
            return true && authors.matches(regex) && authors!=null;
        }

        return false;

    }

}
