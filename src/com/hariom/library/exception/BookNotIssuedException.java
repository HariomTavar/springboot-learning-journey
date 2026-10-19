package com.hariom.library.exception;

// Thrown when a member tries to return a book they never took
public class BookNotIssuedException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookNotIssuedException(String title, String memberName) {
        super(memberName + " has not issued the book: " + title);
    }
}