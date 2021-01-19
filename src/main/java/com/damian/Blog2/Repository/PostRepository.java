package com.damian.Blog2.Repository;

import com.damian.Blog2.Models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
