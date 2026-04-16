package com.airtribe.entity;


import com.airtribe.enums.BookStatus;

public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private final int publicationYear;
    private BookStatus status;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public BookStatus getStatus() { return status; }

    public void setStatus(BookStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %s by %s (%d) - Status: %s",
                isbn, title, author, publicationYear, status);
    }
}
