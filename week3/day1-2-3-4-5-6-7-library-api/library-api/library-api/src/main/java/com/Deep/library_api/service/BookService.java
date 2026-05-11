package com.Deep.library_api.service;

import com.Deep.library_api.exception.BookNotFoundException;
import com.Deep.library_api.model.Author;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.model.CreateBookRequest;
import com.Deep.library_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final EmailNotificationService emailNotificationService;

    public BookService(BookRepository bookRepo, EmailNotificationService emailNotificationService) {
        this.bookRepo = bookRepo;
        this.emailNotificationService = emailNotificationService;
    }

    @Cacheable(value = "allBooks", key = "'all'") //The inner single quotes ('all') tell SpEL: "this is a string literal, not a variable"
    public List<Book> getAllBooks() {
        log.info("Fetching all books");
        return bookRepo.findAll();
    }
    @Cacheable(value = "books", key = "#id")
    //value = "books" — cache name (bucket). All getBookById() results go into a cache named "books"
    //key = "#id" — cache key. The actual key in Redis will be "books::1", "books::2", etc. The #id is SpEL (Spring Expression Language) — it means "use the id parameter as the key"
    public Book getBookById(Long id) {
        log.info("Fetching book by id={}",id);
        return bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book not found, id ={}", id);
            return new BookNotFoundException(id);
        });
    }

    @CacheEvict(value = "allBooks", allEntries = true)
    public Book addBook(CreateBookRequest request) {
        log.info("Adding Book to repo");
        Author author = new Author();
        author.setName(request.getAuthor().getName());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setAuthor(author);
        book.setAvailable(request.isAvailable());

        return bookRepo.save(book);
    }
    @CacheEvict(value = {"allBooks", "books"}, allEntries = true)
    // allBooks — list changed, need to invalidate entire cache
    // books — specific book deleted, evict all entries (safer)
    public void removeBook(Long id) {
        log.info("Removing book from repo ,id={}",id);
        bookRepo.delete(bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book not found, id={}",id);
            return new BookNotFoundException(id);
        }));
    }

    @CacheEvict(value = "allBooks", allEntries = true)
    @CachePut(value = "books", key = "#id")
    // @CachePut — updates the specific book::id in cache
    // @CacheEvict on allBooks — list changed, invalidate full list
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
        log.info("Issuing Book from library ,Id={}", id);
        Book book = bookRepo.findById(id).orElseThrow(() -> {
            log.warn("Book that you tried to issue id={} is not present", id);
            return new BookNotFoundException(id);
        });
        if (!book.isAvailable()) {
            log.warn("Book already borrowed, id={}", id);
            throw new RuntimeException("Book is already borrowed");
        }
        book.setAvailable(false);
        bookRepo.save(book);

        // Fire async email notification — caller doesn't wait
        emailNotificationService.sendBorrowConfirmationEmail(
                "user@library.com",  // In real code, fetch user email from database or auth
                book.getTitle(),
                book.getId()
        );
        log.info("Borrow confirmation email fired asynchronously for book id={}", id);
    }
}
