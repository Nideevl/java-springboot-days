package com.Deep.library_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/books")
    public String getBooks() {
        return "books endpoint working";
    }
}