package com.Deep.library_api.repository;

import com.Deep.library_api.model.Book;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class BookRepository {

    public List<Book> findAll() {
        return List.of(
                new Book(1L, "Clean Code", "Robert Martin"),
                new Book(2L, "Effective Java", "Joshua Bloch")
        );
    }
}
