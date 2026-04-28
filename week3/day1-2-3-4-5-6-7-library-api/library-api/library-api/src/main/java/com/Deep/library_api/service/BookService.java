package com.Deep.library_api.service;

import com.Deep.library_api.exception.BookNotFoundException;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        log.info("Fetching all books");
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) {
        log.info("Fetching book by id={}",id);
        return bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book not found, id ={}", id);
            return new BookNotFoundException(id);
        });
    }

    public Book addBook(Book book) {
        log.info("Adding Book to repo");
        return bookRepo.save(book);
    }

    public void removeBook(Long id) {
        log.info("Removing book from repo ,id={}",id);
        bookRepo.delete(bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book not found, id={}",id);
            return new BookNotFoundException(id);
        }));
    }

    public Book updateBook(Long id, Book updatedBook) {
        log.info("Updating book details using Id");
        bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book was not found ,id={}",id);
            return new BookNotFoundException(id);
        });

        updatedBook.setId(id);
        return bookRepo.save(updatedBook);
    }
    public Page<Book> getBooks(int page, int size, String sortBy) {
            log.info("Fetching Books in sections");
            return bookRepo.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }
    public List<Book> getBooksByGenre(String genre) {
        log.info("Fetching books by Genre:{}",genre);
        return bookRepo.findByGenre(genre);
    }

    public List<Book> searchBooksByTitle(String title) {
        log.info("Searching Book with title = {}",title);
        return bookRepo.searchByTitle(title);
    }

    @Transactional
    public void borrowedBook(Long id) {
        log.info("Issuing Book from library ,Id={}",id);
        Book book = bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book that you tried to issue id={} is not present",id);
            return new BookNotFoundException(id);
        });
        if (!book.isAvailable()) {
            log.warn("Book already borrowed, id={}", id);
            throw new RuntimeException("Book is already borrowed");
        }
        book.setAvailable(false);
        bookRepo.save(book);
    }
}
