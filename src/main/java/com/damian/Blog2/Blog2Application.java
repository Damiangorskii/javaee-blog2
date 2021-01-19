package com.damian.Blog2;

import com.damian.Blog2.Models.Author;
import com.damian.Blog2.Models.User;
import com.damian.Blog2.Repository.AuthorRepository;
import com.damian.Blog2.Repository.UserRepository;
import com.damian.Blog2.Service.AuthorService;
import com.damian.Blog2.Service.CommentService;
import com.damian.Blog2.Service.PostService;
import com.damian.Blog2.Service.TagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.io.IOException;
import java.util.Locale;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class Blog2Application {


	public static void main(String[] args) throws IOException {

		SpringApplication.run(Blog2Application.class, args);

	}

	@Bean
	public static CommandLineRunner loadData(AuthorService as, TagService ts, PostService ps, CommentService cs) {
		return (args) -> {
			as.saveAuthorData();
			ts.saveTagData();
			ps.savePostData();
			cs.saveCommentData();
		};
	}

	@Bean
	public static CommandLineRunner loadAdmin(UserRepository userRepository){
		return (args) -> {
			User user = new User();
			user.setUsername("damian");
			user.setPassword("Password123!");
			user.setEmail("damian.gorski1@wp.pl");
			user.setRole("ADMIN");
			user.setActive(true);
			userRepository.save(user);
		};
	}

	@Bean
	public static CommandLineRunner loadUsers(UserRepository userRepository, AuthorRepository authorRepository){
		return (args) -> {
			int count = 1;
			for (Author author: authorRepository.findAll()){
				User user = new User();
				String name = author.getName().toLowerCase(Locale.ROOT);
				String[] data = name.split(" ");
				String username = data[0].charAt(0) + data[1];
				String email = "";
				for (User u : userRepository.findAll()){
					if (username.equals(u.getUsername())){
						username = username +count;
						email = email + username+count + "@gmail.com";
						count++;
					}
				}
				if (email.equals("")){
					email = email + username + "@gmail.com";
				}
				user.setUsername(username);
				user.setPassword("Password123!");
				user.setEmail(email);
				user.setRole("USER");
				user.setActive(true);
				userRepository.save(user);
			}
		};
	}



}
