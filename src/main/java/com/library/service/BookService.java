package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // ---- CREATE ----
    public Book saveBook(Book book) {
        // Validate ISBN uniqueness
        Optional<Book> existing = bookRepository.findByIsbn(book.getIsbn());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    // ---- READ ----
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> searchByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    // Custom JOIN query
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthorDetails() {
        return bookRepository.findAllBooksWithAuthorDetails();
    }

    // ---- UPDATE ----
    public Book updateBook(Long id, Book updatedBook, Long authorId) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Check ISBN uniqueness (skip if same book)
        Optional<Book> isbnConflict = bookRepository.findByIsbn(updatedBook.getIsbn());
        if (isbnConflict.isPresent() && !isbnConflict.get().getId().equals(id)) {
            throw new IllegalArgumentException("ISBN '" + updatedBook.getIsbn() + "' is already used by another book.");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        existing.setTitle(updatedBook.getTitle());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setGenre(updatedBook.getGenre());
        existing.setPublishedYear(updatedBook.getPublishedYear());
        existing.setPrice(updatedBook.getPrice());
        existing.setAuthor(author);

        return bookRepository.save(existing);
    }

    // ---- DELETE ----
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
