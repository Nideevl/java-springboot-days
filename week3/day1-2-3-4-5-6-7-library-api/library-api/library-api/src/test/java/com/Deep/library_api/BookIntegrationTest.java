package com.Deep.library_api;

import com.Deep.library_api.model.Author;
import com.Deep.library_api.model.Book;
import com.Deep.library_api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepo;

    private Book savedBook;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                        .apply(springSecurity())
                                .build();

        bookRepo.deleteAll();

        Author author = new Author();
        author.setName("Robert C. Martin");

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setGenre("Technology");
        book.setAuthor(author);
        book.setAvailable(true);

        savedBook = bookRepo.save(book);
    }

    @Test
    @WithMockUser
    void getBooks_returns200AndList() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void getBookById_validId_returns200() throws Exception {
        System.out.println("Book ID: " + savedBook.getId());
        mockMvc.perform(get("/books/" + savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook_validId_returns204ThenNotFound() throws Exception {
        mockMvc.perform(delete("/books/"+savedBook.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/books/" + savedBook.getId()))
                .andExpect(status().isNotFound());
    }

}
