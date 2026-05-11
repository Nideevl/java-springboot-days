package com.Deep.library_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    @Async
    public void sendBorrowConfirmationEmail(String userEmail, String bookTitle, Long bookId) {
        log.info("Sending borrow confirmation email to {} for book: {}  (id={})", userEmail, bookTitle, bookId);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Email sent successfully to {} for book: {}", userEmail, bookTitle);
    }
}
