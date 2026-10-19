package com.hariom.library.exception;

public class LimitExceededException extends Exception {

    private static final long serialVersionUID = 1L;

    public LimitExceededException(String memberName, int maxBooks) {
        super(memberName + " has reached the limit of " + maxBooks + " books");
    }
}