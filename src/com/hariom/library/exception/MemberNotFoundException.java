package com.hariom.library.exception;

public class MemberNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(int memberId) {
        super("Member not found with id: " + memberId);
    }
}