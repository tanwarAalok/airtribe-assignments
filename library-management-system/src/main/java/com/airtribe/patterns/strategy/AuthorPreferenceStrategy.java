package com.airtribe.patterns.strategy;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;
import com.airtribe.enums.BookStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorPreferenceStrategy implements RecommendationStrategy{

    @Override
    public List<Book> recommend(Patron patron, List<Book> inventory) {
        Set<String> readAuthors = new HashSet<>();
        patron.getBorrowingHistory().forEach(b -> readAuthors.add(b.getAuthor()));

        return inventory.stream()
                .filter(b -> b.getStatus() == BookStatus.AVAILABLE)
                .filter(b -> readAuthors.contains(b.getAuthor()))
                .limit(3).toList();
    }
}
