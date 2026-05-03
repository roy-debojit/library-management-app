package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Find author by name (case-insensitive)
    List<Author> findByNameContainingIgnoreCase(String name);

    // Find author by nationality
    List<Author> findByNationality(String nationality);

    // Check if author name already exists (for uniqueness validation)
    Optional<Author> findByNameIgnoreCase(String name);

    // Custom JPQL query: find all authors who have written at least one book
    @Query("SELECT DISTINCT a FROM Author a INNER JOIN a.books b")
    List<Author> findAuthorsWithBooks();

    // Custom query: find authors from a given country who have published after a year
    @Query("SELECT a FROM Author a WHERE a.nationality = :nationality AND a.birthYear > :year")
    List<Author> findByNationalityAndBirthYearAfter(
            @Param("nationality") String nationality,
            @Param("year") Integer year);
}
