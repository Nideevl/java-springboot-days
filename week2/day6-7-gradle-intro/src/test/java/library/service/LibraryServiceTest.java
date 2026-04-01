package library.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {

    @Test
    void calculateFine_noDaysOverdue_returnsZero() {
        assertEquals(0.0, LibraryService.calculateFine(10));
    }
    @Test
    void calculateFine_noDaysOverdue_alsoReturnsZero() {
        assertEquals(0.0, LibraryService.calculateFine(14));
    }
    @Test
    void calculateFine_fourDaysOverdue_returnsEight() {
        assertEquals(8.0, LibraryService.calculateFine(18));
    }
}