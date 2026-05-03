package com.library.entity;

import java.math.BigDecimal;

/**
 * DTO for the custom INNER JOIN query result between Book and Author.
 */
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String isbn;
    private String genre;
    private Integer publishedYear;
    private BigDecimal price;
    private Long authorId;
    private String authorName;
    private String nationality;

    public BookAuthorDTO(Long bookId, String bookTitle, String isbn, String genre,
                         Integer publishedYear, BigDecimal price,
                         Long authorId, String authorName, String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.price = price;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
    }

    // Getters
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public Integer getPublishedYear() { return publishedYear; }
    public BigDecimal getPrice() { return price; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getNationality() { return nationality; }
}
