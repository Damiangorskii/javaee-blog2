package com.damian.Blog2.Models;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentCSV {

    private String id;

    private String username;

    private String idPost;

    private String commentContent;
}