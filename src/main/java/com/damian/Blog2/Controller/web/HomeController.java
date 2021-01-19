package com.damian.Blog2.Controller.web;

import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Models.Comment;
import com.damian.Blog2.Models.Post;
import com.damian.Blog2.Models.User;
import com.damian.Blog2.Repository.CommentRepository;
import com.damian.Blog2.Repository.PostRepository;
import com.damian.Blog2.Repository.UserRepository;
import com.damian.Blog2.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.Errors;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    public HomeController(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/")
    public String home(Model model){
        if (securityService.isLoggedIn()){
            model.addAttribute("username", securityService.getUserLoggedIn());
            List<Post> userPosts = new LinkedList<>();
            for (Post post : postRepository.findAll()){
                for (Author author: post.getAuthors()){
                    String[] data = author.getName().toLowerCase().split(" ");
                    String tempUsername = data[0].charAt(0) + data[1];
                    if (securityService.getUserLoggedIn().equals(tempUsername)){
                        userPosts.add(post);
                    }
                }
            }
            model.addAttribute("posts", userPosts);
        }
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, Errors errors){
        if(errors.hasErrors()){
            return "auth/register";
        }
        user.setRole("USER");
        user.setActive(true);
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            return "errors/invalidUsername";
        }
        for (User u: userRepository.findAll()){
            if (u.getEmail().equals(user.getEmail())){
                return "errors/invalidEmail";
            }
        }
        userRepository.save(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("blogv2@wp.pl");
        mailMessage.setSubject("Congratulations! You have created an account!");
        mailMessage.setText("Congratulations! You have created an account!" + "\n\n" + "Your username: " + user.getUsername() + "\n" + "Your email address: " + user.getEmail() + "\n\n" + "All blog app v.2 team is glad you have decided to use our app :)");
        mailSender.send(mailMessage);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerComment(Model model){
        model.addAttribute("user", new User());
        return "auth/register-comment";
    }

    @PostMapping("/register")
    public String registerUserComment(@Valid @ModelAttribute("user") User user, Errors errors){
        if(errors.hasErrors()){
            return "auth/register-comment";
        }
        long id=0;
        for (Comment comment: commentRepository.findAll()){
            if (id < comment.getId()){
                id = comment.getId();
            }
        }
        user.setUsername(commentRepository.findById(id).get().getUsername());
        user.setRole("USER");
        user.setActive(true);
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            return "errors/invalidUsername";
        }
        for (User u: userRepository.findAll()){
            if (u.getEmail().equals(user.getEmail())){
                return "errors/invalidEmail";
            }
        }
        userRepository.save(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("blogv2@wp.pl");
        mailMessage.setSubject("Congratulations! You have created an account!");
        mailMessage.setText("Congratulations! You have created an account!" + "\n\n" + "Your username: " + user.getUsername() + "\n" + "Your email address: " + user.getEmail() + "\n\n" + "All blog app v.2 team is glad you have decided to use our app :)");
        mailSender.send(mailMessage);
        return "redirect:/";
    }
}
