package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
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
class BookRepositoryTest {

    @Autowired private BookRepository   bookRepository;
    @Autowired private AuthorRepository authorRepository;

    private Author author;
    private Book   book1, book2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = authorRepository.save(new Author("George Orwell", "British", 1903, "Dystopian"));
        book1  = bookRepository.save(new Book("1984", "978-0451524935", "Dystopian", 1949, new BigDecimal("9.99"), author));
        book2  = bookRepository.save(new Book("Animal Farm", "978-0451526342", "Satire",   1945, new BigDecimal("7.50"), author));
    }

    @Test
    void testFindByIsbn() {
        Optional<Book> found = bookRepository.findByIsbn("978-0451524935");
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void testFindByGenreIgnoreCase() {
        List<Book> dystopian = bookRepository.findByGenreIgnoreCase("dystopian");
        assertThat(dystopian).hasSize(1);
        assertThat(dystopian.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    void testFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        List<Book> result = bookRepository.findByTitleContainingIgnoreCase("animal");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIsbn()).isEqualTo("978-0451526342");
    }

    @Test
    void testFindAllBooksWithAuthorDetails_innerJoin() {
        List<BookAuthorDTO> dtos = bookRepository.findAllBooksWithAuthorDetails();
        assertThat(dtos).hasSize(2);

        BookAuthorDTO dto = dtos.stream()
                .filter(d -> d.getBookTitle().equals("1984"))
                .findFirst().orElseThrow();

        assertThat(dto.getAuthorName()).isEqualTo("George Orwell");
        assertThat(dto.getNationality()).isEqualTo("British");
        assertThat(dto.getIsbn()).isEqualTo("978-0451524935");
    }

    @Test
    void testFindByAuthorNameContaining() {
        List<Book> result = bookRepository.findByAuthorNameContaining("orwell");
        assertThat(result).hasSize(2);
    }

    @Test
    void testIsbnUniqueness_duplicateShouldExist() {
        Optional<Book> dup = bookRepository.findByIsbn("978-0451524935");
        assertThat(dup).isPresent();
        assertThat(dup.get().getId()).isEqualTo(book1.getId());
    }
}
