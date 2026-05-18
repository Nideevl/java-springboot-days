package com.Deep.library_api.service;

import com.Deep.library_api.event.BookBorrowedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    @EventListener
    @Async
    public void onBookBorrowed(BookBorrowedEvent event) {
        log.info("Sending borrow confirmation email to {} for book: {}  (id={})",
                event.getUserEmail(), event.getBookTitle(), event.getBookId());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Email sent successfully to {} for book: {}", event.getBookTitle(), event.getBookId());
    }
}
