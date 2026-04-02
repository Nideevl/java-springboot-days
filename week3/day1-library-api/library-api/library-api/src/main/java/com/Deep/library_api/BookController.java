package com.Deep.library_api;

import com.Deep.library_api.model.Book;
import com.Deep.library_api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) { return bookService.getBookById(id); }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) { return bookService.addBook(book); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/books/{id}")
    public void removeBook(@PathVariable Long id) { bookService.removeBook(id); }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }
}