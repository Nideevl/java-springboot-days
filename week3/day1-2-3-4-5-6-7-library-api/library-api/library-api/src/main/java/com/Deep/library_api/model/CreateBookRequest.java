package com.Deep.library_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBookRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Genre cannot be blank")
    private String genre;

    @NotNull(message = "Author is required")
    private AuthorDTO author;

    private boolean available;

    public CreateBookRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}