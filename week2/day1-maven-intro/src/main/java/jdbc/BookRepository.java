package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {
    private Connection conn;

    public BookRepository(Connection conn) {
        this.conn = conn;
    }

    public void addBook(String title, String author, String genre) throws SQLException {
        String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,title);
        stmt.setString(2,author);
        stmt.setString(3,genre);
        stmt.executeUpdate();
        System.out.println("Book Added : "+title);
    }

    public void showAll() throws SQLException {
        String sql = "SELECT * FROM books ORDER BY id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getInt("id")+" | "+rs.getString("title")+" | "+rs.getString("author"));
        }
    }

    public void showById(int id) throws SQLException{
        String sql = "SELECT * FROM books WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        System.out.println(rs.getInt("id")+" | "+rs.getString("title")+" | "+rs.getString("author"));
    }

    public void deleteBook(int id) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String deleteBorrow = "DELETE FROM borrowed WHERE book_id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(deleteBorrow);
            stmt1.setInt(1, id);
            stmt1.executeUpdate(); //  sends it to DB, held in transaction, not committed yet

            String deleteBook = "DELETE FROM books WHERE id = ? RETURNING title";
            PreparedStatement stmt2 = conn.prepareStatement(deleteBook);
            stmt2.setInt(1, id);
            ResultSet rs = stmt2.executeQuery(); // sends it to DB, held in transaction, not committed yet

            if (rs.next()) {
                System.out.println("Book Deleted : " + rs.getString("title"));
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Delete failed, rolled back: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }
    }

