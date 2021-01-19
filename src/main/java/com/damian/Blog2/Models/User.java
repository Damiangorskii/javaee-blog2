package com.damian.Blog2.Models;

import com.damian.Blog2.Validator.User.Email;
import com.damian.Blog2.Validator.User.Password;
import com.damian.Blog2.Validator.User.Username;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="\"user\"")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, name = "username")
    private String username;

    @Password
    private String password;

    @Column(unique = true, name = "email")
    private String email;

    private boolean active;
    private String role;
}
