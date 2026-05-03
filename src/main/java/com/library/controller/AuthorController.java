package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // ---- READ: List all authors ----
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "All Authors");
        return "authors/list";
    }

    // ---- CREATE: Show form ----
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Add New Author");
        return "authors/form";
    }

    // ---- CREATE: Handle form submission ----
    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Author");
            return "authors/form";
        }

        try {
            // Check name uniqueness for new authors (id == null)
            if (author.getId() == null && authorService.isNameTaken(author.getName(), -1L)) {
                result.rejectValue("name", "duplicate", "An author with this name already exists.");
                model.addAttribute("pageTitle", "Add New Author");
                return "authors/form";
            }
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMsg", "Author saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error saving author: " + e.getMessage());
        }
        return "redirect:/authors";
    }

    // ---- UPDATE: Show edit form ----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Author not found.");
            return "redirect:/authors";
        }
        model.addAttribute("author", author.get());
        model.addAttribute("pageTitle", "Edit Author");
        return "authors/form";
    }

    // ---- UPDATE: Handle update submission ----
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        try {
            if (authorService.isNameTaken(author.getName(), id)) {
                result.rejectValue("name", "duplicate", "An author with this name already exists.");
                model.addAttribute("pageTitle", "Edit Author");
                return "authors/form";
            }
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("successMsg", "Author updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error updating author: " + e.getMessage());
        }
        return "redirect:/authors";
    }
}
