package com.airtribe.services;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;
import com.airtribe.enums.BookStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LendingService {

    private final LibraryManager inventory;
    private final Map<String, Queue<Patron>> reservations = new HashMap<>();

    public LendingService(LibraryManager inventory) {
        this.inventory = inventory;
    }

    public void checkout(String isbn, String patronId){
        Book book = inventory.findBookByIsbn(isbn);
        Patron patron = inventory.findPatronById(patronId);

        if(book != null && patron != null && book.getStatus() == BookStatus.AVAILABLE){
            book.setStatus(BookStatus.LOANED);
            patron.addHistory(book);
            System.out.println(patron.getName() + " checked out " + book.getTitle());
        } else {
            System.out.println("Checkout failed for ISBN: " + isbn + " by Patron: " + patronId);
        }
    }

    public void returnBook(String isbn){
        Book book = inventory.findBookByIsbn(isbn);
        if(book != null){
            book.setStatus(BookStatus.AVAILABLE);
            processReservations(isbn, book);
        }
    }

    public void reserveBook(String isbn, String patronId){
        reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(inventory.findPatronById(patronId));
        System.out.println("Reservation added for ISBN: " + isbn + " by Patron: " + patronId);
    }

    private void processReservations(String isbn, Book book){
        Queue<Patron> queue = reservations.get(isbn);
        if(queue != null && !queue.isEmpty()){
            Patron nextPatron = queue.poll();
            nextPatron.update("The book '" + book.getTitle() + "' is now available for you!");
        }
    }
}
