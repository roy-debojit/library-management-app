package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Size(max = 50, message = "Genre must not exceed 50 characters")
    @Column(length = 50)
    private String genre;

    @Column(name = "published_year")
    private Integer publishedYear;

    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    @Column(precision = 8, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "Author is required")
    private Author author;

    // Constructors
    public Book() {}

    public Book(String title, String isbn, String genre, Integer publishedYear, BigDecimal price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.price = price;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', isbn='" + isbn + "'}";
    }
}
