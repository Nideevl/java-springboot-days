package com.Deep.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String post;

    @ManyToMany(mappedBy = "roles")
    List<User> users;

    public Long getId() { return id; }

    public String getPost() { return post; }

    public void setPost(String post) { this.post = post; }
}
