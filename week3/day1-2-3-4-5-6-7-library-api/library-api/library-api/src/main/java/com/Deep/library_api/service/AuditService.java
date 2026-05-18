package com.Deep.library_api.service;

import com.Deep.library_api.event.BookBorrowedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    @EventListener
    public void onBookBorrowed(BookBorrowedEvent event) {
        log.info("AUDIT: Book borrowed - bookId={}, title={}, user={}",
                event.getBookId(), event.getBookTitle(), event.getUserEmail());
        // In real code: write audit record to database
    }
}