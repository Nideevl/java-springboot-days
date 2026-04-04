package com.Deep.library_api.exception;

public class BookNotFoundException extends ApiException {
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}
