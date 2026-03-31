package jdbc;

import library.model.Book;
import library.model.Member;
import library.repository.BookRepository;
import library.repository.MemberRepository;
import library.service.LibraryService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/library",
                "postgres",
                "hpsr@1234"
        );

        System.out.println("Connected : " + conn.isValid(5));

        BookRepository bookRepo = new BookRepository(conn);
        MemberRepository memberRepo = new MemberRepository(conn);
        LibraryService service = new LibraryService(conn, bookRepo, memberRepo);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. List All Books");
            System.out.println("6. List Available Books");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Title: ");
                        String title = sc.nextLine();

                        System.out.print("Author: ");
                        String author = sc.nextLine();

                        System.out.print("Genre: ");
                        String genre = sc.nextLine();

                        Book book = new Book(0, title, author, genre, true);
                        int bookId = bookRepo.save(book);

                        System.out.println("✅ Book added with ID: " + bookId);
                        break;

                    case 2:
                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        Member member = new Member(0, name, email);
                        int memberId = memberRepo.save(member);

                        System.out.println("✅ Member added with ID: " + memberId);
                        break;

                    case 3:
                        System.out.print("Enter Member ID: ");
                        int mId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Book ID: ");
                        int bId = sc.nextInt();
                        sc.nextLine();

                        service.borrowBook(mId, bId);
                        System.out.println("📚 Book borrowed successfully");
                        break;

                    case 4:
                        System.out.print("Enter Book ID: ");
                        int returnBookId = sc.nextInt();
                        sc.nextLine();

                        service.returnBook(returnBookId);
                        System.out.println("🔁 Book returned successfully");
                        break;

                    case 5:
                        List<Book> allBooks = bookRepo.findAll();
                        System.out.println("\n--- All Books ---");
                        for (Book b : allBooks) {
                            System.out.println(b.getId() + " | " +
                                    b.getTitle() + " | " +
                                    b.getAuthor() + " | " +
                                    b.getGenre() + " | " +
                                    (b.isAvailable() ? "Available" : "Borrowed"));
                        }
                        break;

                    case 6:
                        List<Book> availableBooks = bookRepo.findAvailable();
                        System.out.println("\n--- Available Books ---");
                        for (Book b : availableBooks) System.out.println(b);
                        break;

                    case 7:
                        conn.close();
                        System.out.println("👋 Exiting...");
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("⚠ Error: " + e.getMessage());
            }
        }
    }
}