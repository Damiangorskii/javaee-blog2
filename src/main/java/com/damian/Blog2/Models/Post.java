package com.damian.Blog2.Models;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Author> authors;

    @Column(length = 1000)
    private String postContent;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Tag> tags;

}
