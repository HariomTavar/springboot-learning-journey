package com.hariom.library.service;

import com.hariom.library.exception.BookNotAvailableException;
import com.hariom.library.exception.BookNotFoundException;
import com.hariom.library.exception.BookNotIssuedException;
import com.hariom.library.exception.LimitExceededException;
import com.hariom.library.exception.MemberNotFoundException;
import com.hariom.library.model.Book;
import com.hariom.library.model.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// All business logic lives here. Main only talks to this class.
public class Library implements Searchable {

    // key = id, value = object. HashMap gives fast lookup by id.
    private final Map<Integer, Book> books = new HashMap<>();
    private final Map<Integer, Member> members = new HashMap<>();

    // ---------- PRIVATE HELPERS (find or throw) ----------

    private Book findBook(int bookId) throws BookNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        }
        return book;
    }

    private Member findMember(int memberId) throws MemberNotFoundException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        return member;
    }

    // ---------- BOOKS ----------

    public Book addBook(String title, String author) {
        Book book = new Book(title, author);
        books.put(book.getId(), book);
        return book;
    }

    // A book that is currently issued cannot be removed
    public Book removeBook(int bookId) throws BookNotFoundException, BookNotAvailableException {
        Book book = findBook(bookId);
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(book.getTitle());
        }
        books.remove(bookId);
        return book;
    }

    // Sorted by title using Book's Comparable (compareTo)
    public List<Book> listBooks() {
        List<Book> sorted = new ArrayList<>(books.values());
        Collections.sort(sorted);
        return sorted;
    }

    @Override
    public List<Book> searchByTitle(String keyword) {
        String searchTerm = keyword == null ? "" : keyword.trim().toLowerCase();
        List<Book> matches = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(searchTerm)) {
                matches.add(book);
            }
        }
        Collections.sort(matches);
        return matches;
    }

    @Override
    public List<Book> searchByAuthor(String keyword) {
        String searchTerm = keyword == null ? "" : keyword.trim().toLowerCase();
        List<Book> matches = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(searchTerm)) {
                matches.add(book);
            }
        }
        matches.sort(Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return matches;
    }

    public List<Book> listBooksByAuthor() {
        List<Book> sorted = new ArrayList<>(books.values());
        sorted.sort(Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    // ---------- MEMBERS ----------

    public Member registerMember(String name) {
        Member member = new Member(name);
        members.put(member.getId(), member);
        return member;
    }

    // Sorted by id using a Comparator
    public List<Member> listMembers() {
        List<Member> sorted = new ArrayList<>(members.values());
        sorted.sort(Comparator.comparingInt(Member::getId));
        return sorted;
    }

    // ---------- ISSUE AND RETURN ----------

    public void issueBook(int bookId, int memberId)
            throws BookNotFoundException, MemberNotFoundException,
                   BookNotAvailableException, LimitExceededException {

        Book book = findBook(bookId);           // 1. does the book exist?
        Member member = findMember(memberId);   // 2. does the member exist?

        if (!book.isAvailable()) {              // 3. is the book free?
            throw new BookNotAvailableException(book.getTitle());
        }
        if (!member.canIssue()) {               // 4. is the member under the limit?
            throw new LimitExceededException(member.getName(), Member.MAX_BOOKS);
        }

        // all checks passed: update BOTH sides
        book.setAvailable(false);
        member.addIssuedBook(book);
    }

    public void returnBook(int bookId, int memberId)
            throws BookNotFoundException, MemberNotFoundException, BookNotIssuedException {

        Book book = findBook(bookId);
        Member member = findMember(memberId);

        // removeIssuedBook returns false if this member never took the book
        if (!member.removeIssuedBook(book)) {
            throw new BookNotIssuedException(book.getTitle(), member.getName());
        }
        book.setAvailable(true);
    }

    public List<Book> getIssuedBooks(int memberId) throws MemberNotFoundException {
        return findMember(memberId).getIssuedBooks();
    }
}