package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception{
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/library",
                "postgres",
                "hpsr@1234"
        );

        System.out.println("Connected : "+conn.isValid(5));

        BookRepository bookRepo = new BookRepository(conn);
        bookRepo.showAll();
        conn.close();
    }
}
