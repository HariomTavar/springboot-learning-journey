package com.hariom.library.model;

import com.hariom.library.util.IdGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member extends Person {

    public static final int MAX_BOOKS = 3;

    private final List<Book> issuedBooks = new ArrayList<>();

    public Member(String name) {
        super(IdGenerator.nextMemberId(), name);
    }

    @Override
    public String getRole() {
        return "Member";
    }

    public boolean canIssue() {
        return issuedBooks.size() < MAX_BOOKS;
    }

    public void addIssuedBook(Book book) {
        issuedBooks.add(book);
    }

    // returns true if the book was in the list and got removed
    public boolean removeIssuedBook(Book book) {
        return issuedBooks.remove(book);
    }

    // read-only view: outside code cannot change our list directly
    public List<Book> getIssuedBooks() {
        return Collections.unmodifiableList(issuedBooks);
    }

    @Override
    public String toString() {
        return super.toString() + " | Books issued: " + issuedBooks.size() + "/" + MAX_BOOKS;
    }
}