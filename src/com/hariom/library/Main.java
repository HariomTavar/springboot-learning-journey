package com.hariom.library;

import com.hariom.library.exception.BookNotAvailableException;
import com.hariom.library.exception.BookNotFoundException;
import com.hariom.library.exception.BookNotIssuedException;
import com.hariom.library.exception.LimitExceededException;
import com.hariom.library.exception.MemberNotFoundException;
import com.hariom.library.model.Book;
import com.hariom.library.model.Member;
import com.hariom.library.service.Library;

import java.util.List;
import java.util.Scanner;

// Menu only: no business logic here. Everything goes through Library.
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {

        seedSampleData();       // delete this line if you want to start empty

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            try {
                running = handleChoice(choice);
            } catch (BookNotFoundException | MemberNotFoundException
                     | BookNotAvailableException | LimitExceededException
                     | BookNotIssuedException e) {
                System.out.println("ERROR: " + e.getMessage());
            } finally {
                System.out.println("--------------------------------");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    // ---------- MENU ----------

    private static void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT =====");
        System.out.println(" 1. Add book              6. Search by title");
        System.out.println(" 2. Remove book           7. Search by author");
        System.out.println(" 3. List all books        8. Show member's issued books");
        System.out.println(" 4. Register member       9. List books sorted by author");
        System.out.println(" 5. Issue book           10. Return book");
        System.out.println("11. List members          0. Exit");
    }

    // returns false only when the user chooses Exit
    private static boolean handleChoice(int choice)
            throws BookNotFoundException, MemberNotFoundException,
                   BookNotAvailableException, LimitExceededException,
                   BookNotIssuedException {

        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                removeBook();
                break;
            case 3:
                printBooks("All books (sorted by title)", library.listBooks());
                break;
            case 4:
                registerMember();
                break;
            case 5:
                issueBook();
                break;
            case 6:
                printBooks("Title search results", library.searchByTitle(readText("Title keyword: ")));
                break;
            case 7:
                printBooks("Author search results", library.searchByAuthor(readText("Author keyword: ")));
                break;
            case 8:
                showIssuedBooks();
                break;
            case 9:
                printBooks("All books (sorted by author)", library.listBooksByAuthor());
                break;
            case 10:
                returnBook();
                break;
            case 11:
                listMembers();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please pick a number from the menu.");
        }
        return true;
    }

    // ---------- ACTIONS ----------

    private static void addBook() {
        String title = readText("Title: ");
        String author = readText("Author: ");
        Book book = library.addBook(title, author);
        System.out.println("Added: " + book);
    }

    private static void removeBook() throws BookNotFoundException, BookNotAvailableException {
        int id = readInt("Book id to remove: ");
        Book removed = library.removeBook(id);
        System.out.println("Removed: " + removed);
    }

    private static void registerMember() {
        String name = readText("Member name: ");
        Member member = library.registerMember(name);
        System.out.println("Registered: " + member);
    }

    private static void issueBook()
            throws BookNotFoundException, MemberNotFoundException,
                   BookNotAvailableException, LimitExceededException {
        int bookId = readInt("Book id: ");
        int memberId = readInt("Member id: ");
        library.issueBook(bookId, memberId);
        System.out.println("Book " + bookId + " issued to member " + memberId + ".");
    }

    private static void returnBook()
            throws BookNotFoundException, MemberNotFoundException, BookNotIssuedException {
        int bookId = readInt("Book id: ");
        int memberId = readInt("Member id: ");
        library.returnBook(bookId, memberId);
        System.out.println("Book " + bookId + " returned by member " + memberId + ".");
    }

    private static void showIssuedBooks() throws MemberNotFoundException {
        int memberId = readInt("Member id: ");
        printBooks("Books issued to member " + memberId, library.getIssuedBooks(memberId));
    }

    private static void listMembers() {
        List<Member> members = library.listMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
            return;
        }
        System.out.println("--- Members (" + members.size() + ") ---");
        for (Member m : members) {
            System.out.println(m);
        }
    }

    // ---------- PRINT HELPER ----------

    private static void printBooks(String heading, List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(heading).append(" (").append(books.size()).append(") ---");
        for (Book b : books) {
            sb.append("\n").append(b);
        }
        System.out.println(sb);
    }

    // ---------- SAFE INPUT HELPERS ----------

    // keeps asking until the user types a valid whole number (no crash on letters)
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // keeps asking until the user types something that is not empty
    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("This cannot be empty.");
        }
    }

    // ---------- SAMPLE DATA ----------

    private static void seedSampleData() {
        library.addBook("Effective Java", "Joshua Bloch");
        library.addBook("Clean Code", "Robert Martin");
        library.addBook("Head First Java", "Kathy Sierra");
        library.registerMember("Hariom");
        library.registerMember("Riya");
        System.out.println("Sample data loaded: books 101-103, members 1001-1002.");
    }
}