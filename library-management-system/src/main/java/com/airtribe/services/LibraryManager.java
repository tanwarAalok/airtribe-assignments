package com.airtribe.services;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;
import com.airtribe.enums.BookStatus;

import java.util.*;

public class LibraryManager {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();

    public void addBook(Book book) { books.put(book.getIsbn(), book); }
    public void removeBook(String isbn) { books.remove(isbn); }

    public void addPatron(Patron patron) { patrons.put(patron.getId(), patron); }

    public Book findBookByIsbn(String isbn) { return books.get(isbn); }
    public Patron findPatronById(String id) { return patrons.get(id); }

    public List<Book> search(String query){
        return books.values().stream()
                .filter(b -> b.getTitle().contains(query) || b.getAuthor().contains(query) || b.getIsbn().equals(query))
                .toList();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

}
