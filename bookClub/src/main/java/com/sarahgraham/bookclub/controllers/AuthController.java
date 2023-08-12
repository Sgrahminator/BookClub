package com.sarahgraham.bookclub.controllers;

import com.sarahgraham.bookclub.models.LoginUser;
import com.sarahgraham.bookclub.models.User;
import com.sarahgraham.bookclub.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

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
    public String register(@Valid @ModelAttribute("registrationUser") User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("loginUser", new LoginUser());
            return "loginregister.jsp";
        }

        Map<String, String> errors = userService.validateUser(user);
        if (!errors.isEmpty()) {
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                model.addAttribute(entry.getKey() + "Error", entry.getValue());
            }
            model.addAttribute("registrationUser", user);
            model.addAttribute("loginUser", new LoginUser());
            return "loginregister.jsp";
        }

        try {
            User registeredUser = userService.registerUser(user);
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
    public String login(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            if (loginUser.getEmail() == null || loginUser.getEmail().trim().isEmpty()) {
                model.addAttribute("emailError", "Email is required");
            } else {
                model.addAttribute("invalidCredentials", "Invalid Credentials");
            }
            model.addAttribute("registrationUser", new User());
            return "loginregister.jsp";
        }

        User userInDb = userService.findByEmail(loginUser.getEmail());

        if (userInDb == null) {
            model.addAttribute("emailNotFound", "No account exists with that email");
            model.addAttribute("registrationUser", new User());
            return "loginregister.jsp";
        }

        User authenticatedUser = userService.loginUser(loginUser.getEmail(), loginUser.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("userId", authenticatedUser.getId());
            return "redirect:/home";
        }

        model.addAttribute("errorMessage", "Invalid Credentials or Password Mismatch!");
        model.addAttribute("registrationUser", new User());
        return "loginregister.jsp";
    }

    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("name", user.getName());
            return "home.jsp";
        } else {
            return "redirect:/";
        }
    }
}



