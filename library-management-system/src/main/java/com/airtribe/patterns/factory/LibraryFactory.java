package com.airtribe.patterns.factory;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;

public class LibraryFactory {

    public static Book createBook(String isbn, String title, String author, int year){
        return new Book(isbn, title, author, year);
    }

    public static Patron createPatron(String id, String name){
        return new Patron(id, name);
    }
}
