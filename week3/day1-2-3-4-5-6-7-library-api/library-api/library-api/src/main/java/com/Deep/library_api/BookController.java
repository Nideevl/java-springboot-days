package com.Deep.library_api;

import com.Deep.library_api.model.Book;
import com.Deep.library_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/books/genreFilter")
    public List<Book> getBooksByGenre(@RequestParam(defaultValue = "Adventure") String genre){
        return bookService.getBooksByGenre(genre);
    }

    @GetMapping("/books/search")
    public List<Book> searchBooksByTitle(@RequestParam(defaultValue = "A") String title){
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("books/borrow/{id}")
    public void borrowBook(@Valid @PathVariable Long id) { bookService.borrowedBook(id);}

    @GetMapping("/books/paged")
    public Page<Book> getBooksPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String sortBy) {
        return bookService.getBooks(page, size, sortBy);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) { return bookService.addBook(book); }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/books/{id}")
    public void removeBook(@PathVariable Long id) { bookService.removeBook(id); }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id,@Valid @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }
}

