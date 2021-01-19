package com.damian.Blog2.Service;

import com.damian.Blog2.Models.*;
import com.damian.Blog2.Parser.ParserComment;
import com.damian.Blog2.Parser.ParserPost;
import com.damian.Blog2.Repository.CommentRepository;
import com.damian.Blog2.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    private List<CommentCSV> comments = ParserComment.parse();
    private List<PostCSV> posts = ParserPost.parse();

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    public CommentService() throws IOException {
    }

    public void saveCommentData(){
        PostCSV temp = new PostCSV();
        Post post = new Post();
        Iterable<Post> allPosts = postRepository.findAll();
        for (CommentCSV c: comments) {
            Comment com = new Comment();
            for (PostCSV p: posts){
                if (c.getIdPost().equals(p.getId())){
                    temp = p;
                    break;
                }
            }
            for (Post p: allPosts){
                if (p.getPostContent().equals(temp.getPostContent())){
                    post = p;
                    break;
                }
            }
            com.setPost(post);
            com.setUsername(c.getUsername());
            com.setCommentContent(c.getCommentContent());
            commentRepository.save(com);
        }
    }

    public List<Comment> getCommentsByPostId(long id){
        Iterable<Comment> allComments = commentRepository.findAll();
        Post post = postRepository.findById(id).get();
        List<Comment> result = new LinkedList<>();
        for (Comment comment : allComments){
            if (comment.getPost().getId() == (post.getId())){
                result.add(comment);
            }
        }
        return result;
    }

    public void addComment(Comment comment, long postId){
        Post post = postRepository.findById(postId).get();
        Comment newComment = new Comment();
        newComment.setCommentContent(comment.getCommentContent());
        newComment.setPost(post);
        newComment.setUsername(comment.getUsername());
        commentRepository.save(newComment);
    }
}
