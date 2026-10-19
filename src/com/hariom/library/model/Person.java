package com.hariom.library.model;

// Abstract parent: you cannot create a Person object directly
public abstract class Person {

    private final int id;       // final: an id never changes
    private String name;

    protected Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // every child class must say what its role is
    public abstract String getRole();

    @Override
    public String toString() {
        return "[" + getRole() + "] " + id + " - " + name;
    }
}