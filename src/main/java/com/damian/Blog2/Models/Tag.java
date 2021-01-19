package com.damian.Blog2.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    private long id;

    private String tag;

}
