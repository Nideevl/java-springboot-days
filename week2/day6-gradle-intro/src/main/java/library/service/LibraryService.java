package library.service;

import library.exception.BookNotAvailableException;
import library.exception.LibraryException;
import library.model.Book;
import library.repository.BookRepository;
import library.exception.MemberNotFoundException;
import library.repository.MemberRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class LibraryService {

    private Connection conn;
    private BookRepository bookRepo;
    private MemberRepository memberRepo;

    public LibraryService(Connection conn, BookRepository bookRepo, MemberRepository memberRepo) {
        this.conn = conn;
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
    }

    public void borrowBook(int memberId, int bookId) throws SQLException {
        if(memberRepo.findById(memberId).isEmpty())
            throw new MemberNotFoundException("Member not present in the directory");

        Optional<Book> book = bookRepo.findById(bookId);
        if(book.isEmpty() || !book.get().isAvailable())
            throw new BookNotAvailableException("Book is not available in the library");

        conn.setAutoCommit(false);
        try {
            String borrowSql = "INSERT INTO borrowed(book_id, member_id) VALUES(?,?)";
            PreparedStatement borrowStmt = conn.prepareStatement(borrowSql);
            borrowStmt.setInt(1,bookId);
            borrowStmt.setInt(2,memberId);
            borrowStmt.executeUpdate();

            bookRepo.updateAvailabilityStatus(bookId, false);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException(e);
        } finally {
            conn.setAutoCommit(true);
        }

    }

    public void returnBook(int bookId) throws SQLException {
        Optional <Book> book = bookRepo.findById(bookId);
        if(book.isEmpty())
            throw new LibraryException("Book with that id does not exist.");
        else if(book.get().isAvailable())
            throw new LibraryException("Book is already present in the Library.");

        conn.setAutoCommit(false);
        try {
            String dateSql = "SELECT borrow_date FROM borrowed WHERE book_id = ?";
            PreparedStatement dateStmt = conn.prepareStatement(dateSql);
            dateStmt.setInt(1,bookId);
            ResultSet rs = dateStmt.executeQuery();
            rs.next();
            LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
            long daysBorrowed = ChronoUnit.DAYS.between(borrowDate, LocalDate.now());

            double fineAmount = calculateFine(daysBorrowed);

            String updateSql = "UPDATE borrowed SET return_date = CURRENT_DATE, fine_amount = ? WHERE book_id = ? AND return_date IS NULL";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setDouble(1,fineAmount);
            updateStmt.setInt(2,bookId);
            updateStmt.executeUpdate();

            bookRepo.updateAvailabilityStatus(bookId, true);

            conn.commit();

        }catch (SQLException e) {
            conn.rollback();
            throw new SQLException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public double calculateFine(long borrowedDays){
        if(borrowedDays < 14) return 0;
        else return (borrowedDays-14)*2;
    }
}