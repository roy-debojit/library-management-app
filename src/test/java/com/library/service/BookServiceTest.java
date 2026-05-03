package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository   bookRepository;
    @Mock private AuthorRepository authorRepository;

    @InjectMocks private BookService bookService;

    private Author author;
    private Book   book;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903, "Dystopian");
        author.setId(1L);

        book = new Book("1984", "978-0451524935", "Dystopian", 1949, new BigDecimal("9.99"), author);
        book.setId(10L);
    }

    // ---- saveBook ----

    @Test
    void saveBook_success() {
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.saveBook(book);

        assertThat(saved.getTitle()).isEqualTo("1984");
        verify(bookRepository).save(book);
    }

    @Test
    void saveBook_duplicateIsbn_throwsException() {
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.saveBook(book))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");

        verify(bookRepository, never()).save(any());
    }

    // ---- getAllBooks ----

    @Test
    void getAllBooks_returnsList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> books = bookService.getAllBooks();

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getIsbn()).isEqualTo("978-0451524935");
    }

    // ---- getBookById ----

    @Test
    void getBookById_found() {
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(10L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(99L);

        assertThat(result).isEmpty();
    }

    // ---- updateBook ----

    @Test
    void updateBook_success() {
        Book updatedData = new Book("Nineteen Eighty-Four", "978-0451524935", "Dystopian", 1949, new BigDecimal("12.99"), null);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(book)); // same book
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.updateBook(10L, updatedData, 1L);

        assertThat(result.getTitle()).isEqualTo("Nineteen Eighty-Four");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("12.99"));
    }

    @Test
    void updateBook_isbnConflict_throwsException() {
        Book anotherBook = new Book("Animal Farm", "978-0451524935", "Satire", 1945, new BigDecimal("7.99"), author);
        anotherBook.setId(99L); // different id

        Book updatedData = new Book("Edited", "978-0451524935", "Fiction", 2000, BigDecimal.ONE, null);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(anotherBook)); // conflict

        assertThatThrownBy(() -> bookService.updateBook(10L, updatedData, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already used");
    }

    @Test
    void updateBook_notFound_throwsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(99L, book, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    // ---- getAllBooksWithAuthorDetails (JOIN) ----

    @Test
    void getAllBooksWithAuthorDetails_returnsDTOs() {
        BookAuthorDTO dto = new BookAuthorDTO(
                10L, "1984", "978-0451524935", "Dystopian", 1949,
                new BigDecimal("9.99"), 1L, "George Orwell", "British");

        when(bookRepository.findAllBooksWithAuthorDetails()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.getAllBooksWithAuthorDetails();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("George Orwell");
    }
}
