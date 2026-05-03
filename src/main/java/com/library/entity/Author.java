package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 100, message = "Nationality must not exceed 100 characters")
    @Column(length = 100)
    private String nationality;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Size(max = 500, message = "Bio must not exceed 500 characters")
    @Column(length = 500)
    private String bio;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    // Constructors
    public Author() {}

    public Author(String name, String nationality, Integer birthYear, String bio) {
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.bio = bio;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + name + "', nationality='" + nationality + "'}";
    }
}
