package com.hariom.library.model;

import com.hariom.library.util.IdGenerator;

import java.util.Objects;

public class Book implements Comparable<Book> {

    private final int id;
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author) {
        this.id = IdGenerator.nextBookId();
        this.title = title;
        this.author = author;
        this.available = true;      // a new book is always available
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Two books are equal if they have the same id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Natural order: sort by title, ignoring upper/lower case
    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " | " + (available ? "Available" : "Issued");
    }
}