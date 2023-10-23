package com.academy.onlinestore.api.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    // This column defined that the Role table is the owner of relationship
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    @ManyToMany
    private List<Authority> authorities;

}
