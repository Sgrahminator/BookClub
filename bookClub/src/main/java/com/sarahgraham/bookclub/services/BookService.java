package com.sarahgraham.bookclub.services;

import com.sarahgraham.bookclub.models.Book;
import com.sarahgraham.bookclub.models.User;
import com.sarahgraham.bookclub.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        Map<String, String> errors = validateBook(book);
        if (!errors.isEmpty()) {
            String combinedErrors = String.join(" ", errors.values());
            throw new IllegalArgumentException(combinedErrors);
        }
        
        book.setCreatedAt(new Date()); 
        book.setUpdatedAt(new Date()); 
        
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> getBooksByUser(User user) {
        return bookRepository.findByUser(user);
    }
    
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Map<String, String> validateBook(Book book) {
        Map<String, String> errors = new HashMap<>();

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (book.getAuthorName() == null || book.getAuthorName().isEmpty()) {
            errors.put("authorName", "Author Name is required");
        }
        if (book.getPostedBy() == null || book.getPostedBy().isEmpty()) {
            errors.put("postedBy", "Posted by is required");
        }
        if (book.getMyThoughts() == null || book.getMyThoughts().isEmpty()) {
            errors.put("myThoughts", "My thoughts is required");
        }

        return errors;
    }
}


