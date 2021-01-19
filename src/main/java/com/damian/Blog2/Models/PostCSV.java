package com.damian.Blog2.Models;

import com.damian.Blog2.Validator.Post.Authors;
import com.damian.Blog2.Validator.Post.PostContent;
import com.damian.Blog2.Validator.Post.Tags;
import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class PostCSV {

    private String id;

    @Authors
    private String authors;

    @PostContent
    private String postContent;

    @Tags
    private String tags;

}
