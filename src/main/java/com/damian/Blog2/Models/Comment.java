package com.damian.Blog2.Models;


import com.damian.Blog2.Validator.Comment.CommentContent;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private long id;

//    @Column(unique = true, name = "username")
    private String username;

    @OneToOne(cascade = {CascadeType.MERGE})
    private Post post;

    @CommentContent
    @Column(length = 1000)
    private String commentContent;
}