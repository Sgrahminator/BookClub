package com.sarahgraham.bookclub.controllers;

import com.sarahgraham.bookclub.models.Book;
import com.sarahgraham.bookclub.models.User;
import com.sarahgraham.bookclub.services.BookService;
import com.sarahgraham.bookclub.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userService.findById(userId).orElse(null);
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("currentUser", user);
        model.addAttribute("allBooks", allBooks);
        return "home.jsp";
    }

    @RequestMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "newbook.jsp";
    }

    @PostMapping("/new")
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "newbook.jsp";
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId).orElse(null);

        if (user != null) {
            book.setUser(user);
            book.setPostedBy(user.getName()); 
            bookService.createBook(book);
            return "redirect:/books"; 
        } else {
            return "redirect:/"; 
        }
    }

    @RequestMapping("/{id}")
    public String showBook(@PathVariable("id") Long id, Model model, HttpSession session) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/books";
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId).orElse(null);
        model.addAttribute("book", book);
        model.addAttribute("currentUser", user);
        return "thoughts.jsp";
    }

    @RequestMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Long id, Model model, HttpSession session) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/books";
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId).orElse(null);
        if (!book.getUser().equals(user)) {
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        return "edit.jsp";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") Long id, @Valid @ModelAttribute("book") Book book,
                             BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "edit.jsp";
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId).orElse(null);
        Book existingBook = bookService.getBookById(id);
        if (existingBook == null || !existingBook.getUser().equals(user)) {
            return "redirect:/books";
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthorName(book.getAuthorName());
        existingBook.setMyThoughts(book.getMyThoughts());
        bookService.updateBook(existingBook);
        return "redirect:/books";
    }

    @RequestMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId).orElse(null);
        Book book = bookService.getBookById(id);
        if (book != null && book.getUser().equals(user)) {
            bookService.deleteBook(id);
        }
        return "redirect:/books";
    }
}
