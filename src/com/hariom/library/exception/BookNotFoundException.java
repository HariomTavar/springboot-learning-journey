package com.hariom.library.exception;

// Checked exception: extends Exception, so callers MUST handle or declare it
public class BookNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookNotFoundException(int bookId) {
        super("Book not found with id: " + bookId);
    }
}