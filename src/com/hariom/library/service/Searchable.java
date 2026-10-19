package com.hariom.library.service;

import com.hariom.library.model.Book;

import java.util.List;

// A contract: any class that can search books must provide these two methods
public interface Searchable {

    List<Book> searchByTitle(String keyword);

    List<Book> searchByAuthor(String keyword);
}