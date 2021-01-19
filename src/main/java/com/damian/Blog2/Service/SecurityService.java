package com.damian.Blog2.Service;


import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Models.Comment;
import com.damian.Blog2.Models.Post;
import com.damian.Blog2.Models.User;
import com.damian.Blog2.Repository.CommentRepository;
import com.damian.Blog2.Repository.PostRepository;
import com.damian.Blog2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public boolean isPostAuthorOrAdmin(long id){
        if (isLoggedInUserAdmin()){
            return true;
        }
        String currentUser = getUserLoggedIn();
        Post post = postRepository.findById(id).orElseThrow();
        for (Author author: post.getAuthors()){
            String[] data = author.getName().toLowerCase().split(" ");
            if ((data[0].charAt(0) + data[1]).equals(currentUser)){
                return true;
            }
        }
        return false;
    }

    public String getUserLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            return currentUsername;
        }
        return "";
    }

    public boolean isLoggedInUserAdmin(){
        String username = getUserLoggedIn();
        if (username.equals("")){
            return false;
        }
        User user = userRepository.findByUsername(username).get();
        if (user.getRole().equals("ADMIN")){
            return true;
        }
        return false;
    }

    public boolean isCommentOwnerOrAdmin(long id){
        if (isLoggedInUserAdmin()){
            return true;
        }
        String currentUser = getUserLoggedIn();
        if (currentUser.equals("")){
            return false;
        }
        Comment comment = commentRepository.findById(id).get();
        if (comment.getUsername().equals(currentUser)){
            return true;
        }
        return false;
    }

    public boolean isLoggedIn(){
        String currentUser = getUserLoggedIn();
        if (currentUser.equals("")){
            return false;
        }
        return true;
    }

}
