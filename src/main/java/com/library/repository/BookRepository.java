package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);

    // Find books by genre
    List<Book> findByGenreIgnoreCase(String genre);

    // Find books by author ID
    List<Book> findByAuthorId(Long authorId);

    // Find books with title containing keyword
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // ---- Custom INNER JOIN Query ----
    // Performs INNER JOIN between Book and Author entities,
    // returning a DTO with fields from both tables.
    @Query("""
            SELECT new com.library.entity.BookAuthorDTO(
                b.id, b.title, b.isbn, b.genre, b.publishedYear, b.price,
                a.id, a.name, a.nationality
            )
            FROM Book b
            INNER JOIN b.author a
            ORDER BY a.name ASC, b.title ASC
            """)
    List<BookAuthorDTO> findAllBooksWithAuthorDetails();

    // Custom query: find books by author name (partial match)
    @Query("""
            SELECT b FROM Book b
            INNER JOIN b.author a
            WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))
            """)
    List<Book> findByAuthorNameContaining(@Param("authorName") String authorName);

    // Custom query: count books per author
    @Query("SELECT b.author.id, COUNT(b) FROM Book b GROUP BY b.author.id")
    List<Object[]> countBooksByAuthor();
}
