package com.Deep.library_api.model;

import jakarta.validation.constraints.NotBlank;

public class AuthorDTO {
    @NotBlank(message = "Author name cannot be blank")
    private String name;

    public AuthorDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}