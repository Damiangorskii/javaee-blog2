package com.damian.Blog2.Service;

import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Models.Tag;
import com.damian.Blog2.Parser.ParserPost;
import com.damian.Blog2.Repository.AuthorRepository;
import com.damian.Blog2.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TagService {

    private List<String> tags = ParserPost.allTags();

    @Autowired
    private TagRepository tagRepository;

    public TagService() throws IOException {
    }

    public void saveTagData(){
        for (int i=0; i<tags.size(); i++){
            Tag tag = new Tag();

            tag.setTag(tags.get(i));
            tagRepository.save(tag);
        }
    }
}
