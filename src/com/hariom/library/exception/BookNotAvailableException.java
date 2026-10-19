package com.hariom.library.exception;

public class BookNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookNotAvailableException(String title) {
        super("Book is already issued: " + title);
    }
}