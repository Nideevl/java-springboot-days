package com.Deep.library_api;

import com.Deep.library_api.model.Author;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setName("J.K. Rolling");
    }

    @Test
    void saveBook_persistsCorrectly() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setGenre("Technology");
        book.setAuthor(author);
        book.setAvailable(true);

        Book saved = bookRepo.save(book);

        assertNotNull(saved.getId());
        assertEquals("Clean Code", saved.getTitle());
    }

    @Test
    void deleteBook_removesFromDb() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setGenre("Technology");
        book.setAuthor(author);
        book.setAvailable(true);

        Book saved = bookRepo.save(book);
        bookRepo.deleteById(saved.getId());

        assertTrue(bookRepo.findById(saved.getId()).isEmpty());
    }

    @Test
    void findByGenre_returnsMatchingBooks() {
        Book book1 = new Book();
        book1.setTitle("Clean Code");
        book1.setGenre("Technology");
        book1.setAuthor(author);
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setTitle("Design Patterns");
        book2.setGenre("Technology");
        book2.setAuthor(author);
        book2.setAvailable(true);

        bookRepo.save(book1);
        bookRepo.save(book2);

        List<Book> result = bookRepo.findByGenre(("Technology"));

        assertEquals(2, result.size());
    }

    @Test
    void searchByTitle_caseInsensitive() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setGenre("Technology");
        book.setAuthor(author);
        book.setAvailable(true);

        bookRepo.save(book);

        List<Book> results = bookRepo.searchByTitle("clean");

        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());


    }

}
