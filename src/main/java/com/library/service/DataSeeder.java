package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Override
    public void run(String... args) {
        if (authorRepository.count() > 0) return; // Avoid re-seeding

        // --- 10 Authors ---
        Author a1 = authorRepository.save(new Author("George Orwell",      "British",   1903, "Known for dystopian fiction and sharp political commentary."));
        Author a2 = authorRepository.save(new Author("J.K. Rowling",       "British",   1965, "Author of the globally beloved Harry Potter fantasy series."));
        Author a3 = authorRepository.save(new Author("Gabriel Garcia Marquez", "Colombian", 1927, "Pioneer of magical realism and Nobel laureate."));
        Author a4 = authorRepository.save(new Author("Haruki Murakami",    "Japanese",  1949, "Blends surrealism with pop culture in modern Japanese fiction."));
        Author a5 = authorRepository.save(new Author("Toni Morrison",      "American",  1931, "Nobel Prize winner, known for exploring African American identity."));
        Author a6 = authorRepository.save(new Author("Leo Tolstoy",        "Russian",   1828, "Authored epic novels exploring human nature and society."));
        Author a7 = authorRepository.save(new Author("Chimamanda Ngozi Adichie", "Nigerian", 1977, "Feminist author and storyteller from Nigeria."));
        Author a8 = authorRepository.save(new Author("F. Scott Fitzgerald","American",  1896, "Chronicler of the Jazz Age and the American Dream."));
        Author a9 = authorRepository.save(new Author("Isabel Allende",     "Chilean",   1942, "Master of magical realism and family sagas."));
        Author a10= authorRepository.save(new Author("Dostoevsky",         "Russian",   1821, "Explored psychology, morality and existentialism."));

        // --- 10 Books (one per author, variety of genres) ---
        bookRepository.save(new Book("1984",                   "978-0451524935", "Dystopian",    1949, new BigDecimal("9.99"),  a1));
        bookRepository.save(new Book("Harry Potter and the Sorcerer's Stone", "978-0439708180", "Fantasy", 1997, new BigDecimal("12.99"), a2));
        bookRepository.save(new Book("One Hundred Years of Solitude", "978-0060883287", "Magical Realism", 1967, new BigDecimal("14.50"), a3));
        bookRepository.save(new Book("Norwegian Wood",         "978-0375704024", "Literary Fiction", 1987, new BigDecimal("11.99"), a4));
        bookRepository.save(new Book("Beloved",                "978-1400033416", "Historical Fiction", 1987, new BigDecimal("13.50"), a5));
        bookRepository.save(new Book("War and Peace",          "978-1420954309", "Historical Fiction", 1869, new BigDecimal("19.99"), a6));
        bookRepository.save(new Book("Purple Hibiscus",        "978-1616953638", "Literary Fiction", 2003, new BigDecimal("10.99"), a7));
        bookRepository.save(new Book("The Great Gatsby",       "978-0743273565", "Classic",       1925, new BigDecimal("8.99"),  a8));
        bookRepository.save(new Book("The House of the Spirits","978-1501117015","Magical Realism", 1982, new BigDecimal("13.00"), a9));
        bookRepository.save(new Book("Crime and Punishment",   "978-0486415871", "Classic",       1866, new BigDecimal("7.99"),  a10));

        System.out.println("✅ Database seeded with 10 authors and 10 books.");
    }
}
