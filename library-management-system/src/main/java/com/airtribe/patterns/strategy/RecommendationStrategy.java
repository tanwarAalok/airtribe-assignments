package com.airtribe.patterns.strategy;

import com.airtribe.entity.Book;
import com.airtribe.entity.Patron;

import java.util.List;

public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> inventory);
}
