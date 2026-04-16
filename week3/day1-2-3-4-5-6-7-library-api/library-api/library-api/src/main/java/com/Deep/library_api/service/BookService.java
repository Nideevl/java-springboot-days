package com.Deep.library_api.service;

import com.Deep.library_api.exception.BookNotFoundException;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) { return bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id)); }

    public Book addBook(Book book) { return bookRepo.save(book);}

    public void removeBook(Long id) {  bookRepo.delete(bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id))); }

    public Book updateBook(Long id, Book updatedBook) {
        bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        updatedBook.setId(id);
        return bookRepo.save(updatedBook);
    }
    public Page<Book> getBooks(int page, int size, String sortBy) {
            return bookRepo.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }
    public List<Book> getBooksByGenre(String genre) { return bookRepo.findByGenre(genre); }

    public List<Book> searchBooksByTitle(String title) { return bookRepo.searchByTitle(title); }

    @Transactional
    public void borrowedBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }
        book.setAvailable(false);
        bookRepo.save(book);
    }
}
