package com.airtribe;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;
import com.airtribe.patterns.factory.LibraryFactory;
import com.airtribe.patterns.strategy.AuthorPreferenceStrategy;
import com.airtribe.patterns.strategy.RecommendationStrategy;
import com.airtribe.services.LendingService;
import com.airtribe.services.LibraryManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        LibraryManager library = new LibraryManager();
        LendingService lendingService = new LendingService(library);

        Book b1 = LibraryFactory.createBook("123", "Clean Code", "Robert Martin", 2000);
        Book b2 = LibraryFactory.createBook("456", "Design Patterns", "Gang of Four", 2001);
        Patron p1 = LibraryFactory.createPatron("P01", "Alice");
        Patron p2 = LibraryFactory.createPatron("P02", "Bob");

        library.addBook(b1);
        library.addBook(b2);
        library.addPatron(p1);
        library.addPatron(p2);

        lendingService.checkout("123", "P01");
        lendingService.reserveBook("123", "P02");
        lendingService.returnBook("123");

        RecommendationStrategy strategy = new AuthorPreferenceStrategy();
        List<Book> recommendations = strategy.recommend(p1, library.getAllBooks());
        System.out.println("Recommendations: " + recommendations);
    }
}
