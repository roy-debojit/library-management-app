package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository   bookRepository;

    private Author author1, author2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author1 = authorRepository.save(new Author("George Orwell", "British", 1903, "Dystopian author"));
        author2 = authorRepository.save(new Author("J.K. Rowling",  "British", 1965, "Fantasy author"));
        authorRepository.save(new Author("Haruki Murakami", "Japanese", 1949, "Surrealist author"));
    }

    @Test
    void testSaveAndFindById() {
        Optional<Author> found = authorRepository.findById(author1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("George Orwell");
    }

    @Test
    void testFindAll() {
        List<Author> all = authorRepository.findAll();
        assertThat(all).hasSize(3);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Author> result = authorRepository.findByNameContainingIgnoreCase("orwell");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("George Orwell");
    }

    @Test
    void testFindByNationality() {
        List<Author> british = authorRepository.findByNationality("British");
        assertThat(british).hasSize(2);
    }

    @Test
    void testFindByNameIgnoreCase() {
        Optional<Author> found = authorRepository.findByNameIgnoreCase("j.k. rowling");
        assertThat(found).isPresent();
        assertThat(found.get().getNationality()).isEqualTo("British");
    }

    @Test
    void testFindAuthorsWithBooks() {
        // Give author1 a book
        Book book = new Book("1984", "978-0451524935", "Dystopian", 1949, new BigDecimal("9.99"), author1);
        bookRepository.save(book);

        List<Author> authorsWithBooks = authorRepository.findAuthorsWithBooks();
        assertThat(authorsWithBooks).hasSize(1);
        assertThat(authorsWithBooks.get(0).getName()).isEqualTo("George Orwell");
    }

    @Test
    void testUpdateAuthor() {
        author2.setBio("Updated bio for JKR");
        Author updated = authorRepository.save(author2);
        assertThat(updated.getBio()).isEqualTo("Updated bio for JKR");
    }

    @Test
    void testDeleteAuthor() {
        authorRepository.deleteById(author1.getId());
        assertThat(authorRepository.findById(author1.getId())).isEmpty();
    }
}
