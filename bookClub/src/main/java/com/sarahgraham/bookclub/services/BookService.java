package com.sarahgraham.bookclub.services;

import com.sarahgraham.bookclub.models.Book;
import com.sarahgraham.bookclub.repositories.BookRepository;
import com.sarahgraham.bookclub.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserService userServ;

    public Book addBook(Book book, Long userId) {
        User user = userServ.findUserById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        book.setUser(user);
        return bookRepo.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    public List<Book> getBooksByUserId(Long userId) {
        return bookRepo.findByUserId(userId);
    }

    public List<Book> getBooksByPostedBy(String postedBy) {
        return bookRepo.findByPostedBy(postedBy);
    }

    public Book updateBook(Book updatedBook, Long userId) {
        Book existingBook = validateAndGetBook(updatedBook.getId(), userId);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthorName(updatedBook.getAuthorName());
        existingBook.setPostedBy(updatedBook.getPostedBy());
        existingBook.setMyThoughts(updatedBook.getMyThoughts());

        return bookRepo.save(existingBook);
    }

    public void deleteBook(Long bookId, Long userId) {
        Book book = validateAndGetBook(bookId, userId);
        bookRepo.delete(book);
    }

    private Book validateAndGetBook(Long bookId, Long userId) {
        Book book = getBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        
        User user = userServ.findUserById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        if (!book.getUser().equals(user)) {
            throw new IllegalArgumentException("You are not authorized to access this book");
        }
        
        return book;
    }
}

