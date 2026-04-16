package com.airtribe.entity;

import com.airtribe.patterns.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Patron implements Observer {

    private final String id;
    private final String name;
    private final List<Book> borrowingHistory = new ArrayList<>();

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Book> getBorrowingHistory() { return borrowingHistory; }

    public void addHistory(Book book) { borrowingHistory.add(book); }

    @Override
    public void update(String message) {
        System.out.println("LOG [Notification for " + name + "]: " + message);
    }
}
