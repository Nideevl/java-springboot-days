package library.repository;

import library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private Connection conn;

    public BookRepository (Connection conn) { this.conn = conn; }

    public int save(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, genre) VALUES( ? , ? , ? )";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getGenre());

        stmt.executeUpdate();
        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next())
            return keys.getInt(1);
        else return 0;
    }

    public List<Book> findAll() throws SQLException {
        String sql = "SELECT * FROM books";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Book> books = new ArrayList<>();
        while(rs.next()) {
            books.add(new Book(rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getBoolean("available")
            ));
        }
        return books;
    }

    public Optional<Book> findById(int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,bookId);

        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            return Optional.of(new Book(
                    bookId,
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getBoolean("available")
            ));
        } else return Optional.empty();
    }

    public List<Book> findAvailable() throws SQLException {
        String sql = "SELECT * FROM books WHERE available = true";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Book> books = new ArrayList<>();
        while(rs.next()) {
            books.add(new Book(rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    true));
        }
        return books;
    }

    public List<Book> findByGenre (String genre) throws SQLException{
        String sql = "SELECT * FROM books WHERE genre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,genre);
        ResultSet rs = stmt.executeQuery();

        List<Book> books = new ArrayList<>();
        while(rs.next()) {
            books.add(new Book(rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    genre,
                    rs.getBoolean("available")));
        }

        return books;
    }

    public void updateAvailabilityStatus(int bookId, boolean available) throws SQLException{
        String sql = "UPDATE books SET available = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1,available);
        stmt.setInt(2,bookId);
        stmt.executeUpdate() ;
    }
}
