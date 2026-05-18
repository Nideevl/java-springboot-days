package com.Deep.library_api.event;

public class BookBorrowedEvent {
    private final Long bookId;
    private final String bookTitle;
    private final String userEmail;

    public BookBorrowedEvent(long bookId, String bookTitle, String userEmail) {        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.userEmail = userEmail;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
