package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ---- CREATE ----
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // ---- READ ----
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Author> searchByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    @Transactional(readOnly = true)
    public boolean isNameTaken(String name, Long excludeId) {
        Optional<Author> existing = authorRepository.findByNameIgnoreCase(name);
        if (existing.isEmpty()) return false;
        // Allow the same author to keep its own name during update
        return !existing.get().getId().equals(excludeId);
    }

    // ---- UPDATE ----
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        existing.setName(updatedAuthor.getName());
        existing.setNationality(updatedAuthor.getNationality());
        existing.setBirthYear(updatedAuthor.getBirthYear());
        existing.setBio(updatedAuthor.getBio());
        return authorRepository.save(existing);
    }

    // ---- DELETE (bonus) ----
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
