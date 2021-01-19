package com.damian.Blog2.Controller.web;


import com.damian.Blog2.Models.Comment;
import com.damian.Blog2.Repository.CommentRepository;
import com.damian.Blog2.Service.CommentService;
import com.damian.Blog2.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/comment/create/{postId}")
    public String commentCreate(Model model, @PathVariable long postId){
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "comment/commentForm";
    }

    @PostMapping("/comment/create/{postId}")
    public String commentCreation(@Valid Comment comment, Errors errors, @PathVariable long postId){
        if(errors.hasErrors()){
            return "comment/commentForm";
        }
        if (securityService.isLoggedIn()){
            String username = securityService.getUserLoggedIn();
            comment.setUsername(username);
        }
        commentService.addComment(comment, postId);

        if (!securityService.isLoggedIn()){
            return "redirect:/register";
        }

        return "redirect:/post/detail/{postId}";
    }


    @PreAuthorize("@securityService.isCommentOwnerOrAdmin(#id)")
    @GetMapping("/comment/delete/{postId}/{id}")
    public String deleteComment(@PathVariable long id, @PathVariable long postId){
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);

        return "redirect:/post/detail/{postId}";
    }

    @PreAuthorize("@securityService.isCommentOwnerOrAdmin(#id)")
    @GetMapping("/comment/edit/{postId}/{id}")
    public String commentEdit(Model model, @PathVariable long id, @PathVariable long postId ) throws ParseException {
        model.addAttribute("comment", commentRepository.findById(id));
        model.addAttribute("postId", postId);
        return "comment/commentEdit";
    }

    @PreAuthorize("@securityService.isCommentOwnerOrAdmin(#id)")
    @PostMapping("/comment/edit/{postId}/{id}")
    public String commentEdited(Comment comment, @PathVariable long id, @PathVariable long postId){
//        if(errors.hasErrors()){
//            return "comment/commentEdit";
//        }
        Comment com = commentRepository.findById(id).get();
        com.setCommentContent(comment.getCommentContent());
        commentRepository.save(com);

        return "redirect:/post/detail/{postId}";
    }

}
