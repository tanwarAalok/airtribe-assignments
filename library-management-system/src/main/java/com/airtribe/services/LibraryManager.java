package com.airtribe.services;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;
import com.airtribe.enums.BookStatus;

import java.util.*;

public class LibraryManager {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();

    private final Map<String, Queue<Patron>> reservations = new HashMap<>();


    public void addBook(Book book){
        books.put(book.getIsbn(), book);
    }

    public List<Book> search(String query){
        return books.values().stream()
                .filter(b -> b.getTitle().contains(query) || b.getAuthor().contains(query) || b.getIsbn().equals(query))
                .toList();
    }

    public void checkout(String isbn, String patronId){
        Book book = books.get(isbn);
        Patron patron = patrons.get(patronId);

        if(book != null && patron != null && book.getStatus() == BookStatus.AVAILABLE){
            book.setStatus(BookStatus.LOANED);
            patron.addHistory(book);
            System.out.println(patron.getName() + " checked out " + book.getTitle());
        } else {
            System.out.println("Checkout failed for ISBN: " + isbn + " by Patron: " + patronId);
        }
    }

    public void returnBook(String isbn){
        Book book = books.get(isbn);
        if(book != null){
            book.setStatus(BookStatus.AVAILABLE);

            if(reservations.containsKey(isbn) && !reservations.get(isbn).isEmpty()){
                Patron nextInLine = reservations.get(isbn).poll();
                nextInLine.update("The book '" + book.getTitle() + "' is now available for you!");
            }
        }
    }

    public void reserveBook(String isbn, String patronId){
        reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(patrons.get(patronId));
        System.out.println("Reservation added for ISBN: " + isbn + " by Patron: " + patronId);
    }

    public void addPatron(Patron patron){
        patrons.put(patron.getId(), patron);
    }
}
