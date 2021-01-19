package com.damian.Blog2.Service;

import com.damian.Blog2.Models.*;
import com.damian.Blog2.Parser.ParserPost;
import com.damian.Blog2.Repository.AuthorRepository;
import com.damian.Blog2.Repository.CommentRepository;
import com.damian.Blog2.Repository.PostRepository;
import com.damian.Blog2.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    private List<PostCSV> posts = ParserPost.parse();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;


    public PostService() throws IOException {
    }

    public void savePostData() {
        for (int i = 0; i < posts.size(); i++) {
            Post p = new Post();

            List<Author> authors = new LinkedList<>();
            Iterable<Author> allAuthors = authorRepository.findAll();
            for (Author a : allAuthors){
                if (posts.get(i).getAuthors().contains(a.getName())){
                    authors.add(a);
                }
            }
            p.setAuthors(authors);

            List<Tag> tags = new LinkedList<>();
            Iterable<Tag> allTags = tagRepository.findAll();
            for (Tag t : allTags){
                String[] data = posts.get(i).getTags().split(" ");
                for (int j=0; j< data.length; j++){
                    if (data[j].equals(t.getTag())){
                        tags.add(t);
                    }
                }
            }
            p.setTags(tags);

            p.setPostContent(posts.get(i).getPostContent());

            postRepository.save(p);
        }
    }

    public void addPost(PostCSV post) {
        Post newPost = new Post();
        List<Author> authors = new LinkedList<>();
        List<Tag> tags = new LinkedList<>();
        String[] data1 = post.getAuthors().split(", ");
        String[] data2 = post.getTags().split(" ");
        for (int i=0; i< data1.length; i++){
            for (Author author: authorRepository.findAll()){
                if (data1[i].equals(author.getName())){
                    authors.add(author);
                }
            }
        }
        for (int i=0; i< data2.length; i++){
            for (Tag tag: tagRepository.findAll()){
                if (data2[i].equals(tag.getTag())){
                    tags.add(tag);
                }
            }
            if (tags.size()!=i+1){
                Tag newTag = new Tag();
                newTag.setTag(data2[i]);
                tagRepository.save(newTag);
                tags.add(newTag);
            }
        }
        newPost.setAuthors(authors);
        newPost.setPostContent(post.getPostContent());
        newPost.setTags(tags);
        postRepository.save(newPost);
    }

    public void deletePost(long id) {
        Optional<Post> p = postRepository.findById(id);
        Post post = p.get();

        Iterable<Comment> comments = commentRepository.findAll();
        for (Comment comment: comments){
            if (comment.getPost() == post){
                commentRepository.delete(comment);
            }
        }

        postRepository.delete(post);
    }

    public Post getPostById(long id) {
        Optional<Post> p = postRepository.findById(id);
        Post post = p.get();
        return post;
    }

    public void editPost(PostCSV post, long id){
        Post postToUpdate = postRepository.findById(id).get();
        List<Author> authors = new LinkedList<>();
        List<Tag> tags = new LinkedList<>();
        String[] data1 = post.getAuthors().split(", ");
        String[] data2 = post.getTags().split(" ");
        for (int i=0 ;i<data1.length; i++){
            for (Author a: authorRepository.findAll()){
                if (a.getName().equals(data1[i])){
                    authors.add(a);
                }
            }
        }
        for (int i=0; i< data2.length; i++){
            for (Tag t: tagRepository.findAll()){
                if (t.getTag().equals(data2[i])){
                    tags.add(t);
                }
            }
        }
        postToUpdate.setPostContent(post.getPostContent());
        postToUpdate.setAuthors(authors);
        postToUpdate.setTags(tags);
        postRepository.save(postToUpdate);
    }
}
