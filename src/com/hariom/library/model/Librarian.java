package com.hariom.library.model;

import com.hariom.library.util.IdGenerator;

public class Librarian extends Person {

    private final String employeeCode;

    public Librarian(String name, String employeeCode) {
        super(IdGenerator.nextMemberId(), name);
        this.employeeCode = employeeCode;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    @Override
    public String toString() {
        return super.toString() + " | Code: " + employeeCode;
    }
}