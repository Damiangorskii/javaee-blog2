package com.damian.Blog2.Controller.web;


import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Models.Post;
import com.damian.Blog2.Models.PostCSV;
import com.damian.Blog2.Models.Tag;
import com.damian.Blog2.Repository.PostRepository;
import com.damian.Blog2.Service.CommentService;
import com.damian.Blog2.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/post/posts")
    public String home(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post/posts";
    }

    @GetMapping("/post/create")
    public String postCreate(Model model){
        model.addAttribute("post", new PostCSV());
        return "post/postForm";
    }

    @PostMapping("/post/create")
    public String postCreation(@Valid @ModelAttribute("post") PostCSV post, Errors errors, Model model){
        if(errors.hasErrors()){
            return "post/postForm";
        }
        postService.addPost(post);

        return "redirect:/post/posts";
    }

    @PreAuthorize("@securityService.isPostAuthorOrAdmin(#id)")
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable long id){
        postService.deletePost(id);

        return "redirect:/post/posts";
    }



    @GetMapping("/post/detail/{id}")
    public String postDetailInfo(Model model, @PathVariable long id) {
        model.addAttribute("postDetail", postService.getPostById(id));
        model.addAttribute("comments", commentService.getCommentsByPostId(id));
        return "post/postDetail";
    }


    @PreAuthorize("@securityService.isPostAuthorOrAdmin(#id)")
    @GetMapping("/post/edit/{id}")
    public String postEdit(Model model, @PathVariable long id) throws ParseException{
        PostCSV post = new PostCSV();
        Post p = postRepository.findById(id).get();
        String authors = "";
        String tags = "";
        for (Author a: p.getAuthors()){
            authors += a.getName() + ", ";
        }
        for (Tag t: p.getTags()){
            tags += t.getTag() + " ";
        }
        authors = authors.substring(0, authors.length()-2);
        tags = tags.substring(0, tags.length()-1);
        post.setPostContent(p.getPostContent());
        post.setAuthors(authors);
        post.setTags(tags);
        model.addAttribute("post", post);
        return "post/postEdit";
    }


    @PreAuthorize("@securityService.isPostAuthorOrAdmin(#id)")
    @PostMapping("/post/edit/{id}")
    public String postEdited(@Valid @ModelAttribute("post") PostCSV post, Errors errors, @PathVariable long id){
        if(errors.hasErrors()){
            return "post/postEdit";
        }
        postService.editPost(post, id);

        return "redirect:/post/detail/{id}";
    }


}
