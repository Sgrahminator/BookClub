package com.sarahgraham.bookclub.controllers;

import com.sarahgraham.bookclub.models.Book;
import com.sarahgraham.bookclub.models.LoginUser;
import com.sarahgraham.bookclub.models.User;
import com.sarahgraham.bookclub.services.BookService;
import com.sarahgraham.bookclub.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    private BookService bookServ;

    @Autowired
    private UserService userServ;

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("registrationSuccess") != null) {
            model.addAttribute("successMessage", "You've been registered");
            session.removeAttribute("registrationSuccess");
        }

        model.addAttribute("registrationUser", new User());
        model.addAttribute("loginUser", new LoginUser());
        return "loginregister.jsp";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("registrationUser") User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("loginUser", new LoginUser());
            return "loginregister.jsp";
        }

        Map<String, String> errors = userServ.validateUser(user);
        if (!errors.isEmpty()) {
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                model.addAttribute(entry.getKey() + "Error", entry.getValue());
            }
            model.addAttribute("registrationUser", user);
            model.addAttribute("loginUser", new LoginUser());
            return "loginregister.jsp";
        }

        try {
            User registeredUser = userServ.registerUser(user);
            session.setAttribute("userId", registeredUser.getId());
            session.setAttribute("registrationSuccess", true);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registrationUser", user);
            model.addAttribute("loginUser", new LoginUser());
            return "loginregister.jsp";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("registrationUser", new User());
            return "loginregister.jsp";
        }

        try {
            User authenticatedUser = userServ.loginUser(loginUser.getEmail(), loginUser.getPassword());
            session.setAttribute("userId", authenticatedUser.getId());
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registrationUser", new User());
            return "loginregister.jsp";
        }
    }

    @GetMapping("/books")
    public String books(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userServ.findUserById(userId).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        List<Book> allBooks = bookServ.getAllBooks();
        model.addAttribute("allBooks", allBooks);
        model.addAttribute("user", user);
        model.addAttribute("welcomeMessage", "Welcome, " + user.getName());

        return "books.jsp";
    }

    @GetMapping("/books/new")
    public String newBook(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userServ.findUserById(userId).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        Book newBook = new Book();
        newBook.setPostedBy(user.getName()); 
        model.addAttribute("book", newBook);

        return "newbook.jsp";
    }

    @PostMapping("/books/new")
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "newbook.jsp";
        }

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        bookServ.addBook(book, userId);
        return "redirect:/books";
    }

    @GetMapping("/books/{bookId}")
    public String viewBook(@PathVariable("bookId") Long bookId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Book book = bookServ.getBookById(bookId);
        if (book == null) {
            return "redirect:/books";
        }

        User user = userServ.findUserById(userId).orElse(null);
        boolean isUserOwner = user != null && book.getUser().equals(user);
        
        boolean isCurrentUser = user != null && book.getUser().getId().equals(userId);
        
        model.addAttribute("bookId", book.getId());
        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("bookAuthorName", book.getAuthorName());
        model.addAttribute("bookPostedBy", book.getPostedBy());
        model.addAttribute("bookMyThoughts", book.getMyThoughts());
        model.addAttribute("isUserOwner", isUserOwner);
        model.addAttribute("isCurrentUser", isCurrentUser);

        return "thoughts.jsp";
    }


    @GetMapping("/books/{bookId}/edit")
    public String editBook(@PathVariable("bookId") Long bookId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Book book = bookServ.getBookById(bookId);
        if (book == null) {
            return "redirect:/books";
        }

        User user = userServ.findUserById(userId).orElse(null);
        if (user == null || !book.getUser().equals(user)) {
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        return "editbook.jsp";
    }

    @PostMapping("/books/{bookId}/edit")
    public String updateBook(
            @PathVariable("bookId") Long bookId,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "editbook.jsp";
        }

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        book.setId(bookId);
        bookServ.updateBook(book, userId);
        return "redirect:/books";
    }

    @PostMapping("/books/{bookId}/delete")
    public String deleteBook(
            @PathVariable("bookId") Long bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Book book = bookServ.getBookById(bookId);
        if (book != null && book.getUser().getId().equals(userId)) {
            bookServ.deleteBook(bookId, userId);
        }

        return "redirect:/books";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
