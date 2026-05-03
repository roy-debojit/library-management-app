package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock private AuthorRepository authorRepository;

    @InjectMocks private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903, "Dystopian author");
        author.setId(1L);
    }

    @Test
    void saveAuthor_success() {
        when(authorRepository.save(author)).thenReturn(author);

        Author saved = authorService.saveAuthor(author);

        assertThat(saved.getName()).isEqualTo("George Orwell");
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void getAllAuthors_returnsList() {
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> result = authorService.getAllAuthors();

        assertThat(result).hasSize(1);
    }

    @Test
    void getAuthorById_found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getNationality()).isEqualTo("British");
    }

    @Test
    void getAuthorById_notFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Author> result = authorService.getAuthorById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void isNameTaken_returnsTrue_whenDifferentAuthorHasSameName() {
        Author other = new Author("George Orwell", "American", 1900, "Different");
        other.setId(55L);

        when(authorRepository.findByNameIgnoreCase("George Orwell")).thenReturn(Optional.of(other));

        boolean taken = authorService.isNameTaken("George Orwell", 1L); // checking for author id=1
        assertThat(taken).isTrue();
    }

    @Test
    void isNameTaken_returnsFalse_forSameAuthor() {
        when(authorRepository.findByNameIgnoreCase("George Orwell")).thenReturn(Optional.of(author));

        boolean taken = authorService.isNameTaken("George Orwell", 1L);
        assertThat(taken).isFalse(); // same id, so not taken
    }

    @Test
    void updateAuthor_success() {
        Author updated = new Author("Eric Blair", "British", 1903, "Real name of Orwell");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.updateAuthor(1L, updated);

        assertThat(result.getName()).isEqualTo("Eric Blair");
        assertThat(result.getBio()).isEqualTo("Real name of Orwell");
    }

    @Test
    void updateAuthor_notFound_throwsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.updateAuthor(99L, author))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void getAuthorsWithBooks_callsRepository() {
        when(authorRepository.findAuthorsWithBooks()).thenReturn(List.of(author));

        List<Author> result = authorService.getAuthorsWithBooks();

        assertThat(result).hasSize(1);
        verify(authorRepository).findAuthorsWithBooks();
    }
}
