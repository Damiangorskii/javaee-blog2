package com.damian.Blog2.Service;

import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Parser.ParserPost;
import com.damian.Blog2.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class AuthorService {

    private List<String> authors = ParserPost.allAuthors();

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorService() throws IOException {
    }

    public void saveAuthorData(){
        for (int i=0; i<authors.size(); i++){
            Author auth = new Author();

            auth.setName(authors.get(i));
            authorRepository.save(auth);
        }
    }
}
