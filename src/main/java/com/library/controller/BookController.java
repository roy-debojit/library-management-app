package com.library.controller;

import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // ---- READ: List all books with author details (JOIN) ----
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("bookDetails", bookService.getAllBooksWithAuthorDetails());
        model.addAttribute("pageTitle", "All Books");
        return "books/list";
    }

    // ---- CREATE: Show form ----
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Add New Book");
        return "books/form";
    }

    // ---- CREATE: Handle form submission ----
    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           @RequestParam("authorId") Long authorId,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Add New Book");
            return "books/form";
        }

        try {
            authorService.getAuthorById(authorId).ifPresent(book::setAuthor);
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMsg", "Book saved successfully!");
        } catch (IllegalArgumentException e) {
            // ISBN already exists
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Add New Book");
            return "books/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error saving book: " + e.getMessage());
        }
        return "redirect:/books";
    }

    // ---- UPDATE: Show edit form ----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Book not found.");
            return "redirect:/books";
        }
        model.addAttribute("book", book.get());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Edit Book");
        return "books/form";
    }

    // ---- UPDATE: Handle update ----
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             @RequestParam("authorId") Long authorId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }

        try {
            bookService.updateBook(id, book, authorId);
            redirectAttributes.addFlashAttribute("successMsg", "Book updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error updating book: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
