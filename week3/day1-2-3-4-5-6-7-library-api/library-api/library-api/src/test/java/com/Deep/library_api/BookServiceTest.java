package com.Deep.library_api;

import com.Deep.library_api.exception.BookNotFoundException;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import com.Deep.library_api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // tells JUnit to use Mockito for @Mock and @InjectMocks
public class BookServiceTest {

    @Mock
    private BookRepository bookRepo; // fake repo — no DB connection

    @InjectMocks
    private BookService bookService; // real service, but with fake repo injected into it

    private Book book; // shared across all tests, recreated fresh before each one

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAvailable(true); // starts as available before every test
    }

    @Test
    void getBookById_validId_returnsBook() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book)); // fake repo returns book when asked for id 1

        Book result = bookService.getBookById(1L); // calls → bookRepo.findById(1L).orElseThrow()

        assertEquals("Clean Code", result.getTitle()); // checks service returned the right book
    }

    @Test
    void getBookById_invalidId_throwsException() {
        when(bookRepo.findById(99L)).thenReturn(Optional.empty()); // fake repo returns nothing for id 99

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(99L)); // checks orElseThrow() fires
    }

    @Test
    void borrowedBook_availableBook_setsAvailableFalse() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book)); // fake repo returns available book

        bookService.borrowedBook(1L); // calls → finds book → checks available → sets false

        assertFalse(book.isAvailable()); // checks book.setAvailable(false) actually ran
    }

    @Test
    void borrowedBook_unavailableBook_throwsException() {
        book.setAvailable(false); // manually make book unavailable before the call
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book)); // fake repo returns that unavailable book

        assertThrows(RuntimeException.class, () -> bookService.borrowedBook(1L)); // checks if(!available) throw fires
    }
}