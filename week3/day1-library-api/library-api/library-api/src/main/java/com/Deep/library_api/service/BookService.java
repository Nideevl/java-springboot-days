package com.Deep.library_api.service;

import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) { return bookRepo.findById(id).orElseThrow(); }

    public Book addBook(Book book) { return bookRepo.save(book);}

    public void removeBook(Long id) {  bookRepo.delete(bookRepo.findById(id).orElseThrow()); };

    public Book updateBook(Long id, Book updatedBook) {
        bookRepo.findById(id).orElseThrow();
        updatedBook.setId(id);
        return bookRepo.save(updatedBook);
    }

}
