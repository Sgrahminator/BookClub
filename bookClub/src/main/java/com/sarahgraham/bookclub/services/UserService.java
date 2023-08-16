package com.sarahgraham.bookclub.services;

import com.sarahgraham.bookclub.models.User;
import com.sarahgraham.bookclub.repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User registerUser(User user) {
        Map<String, String> errors = validateUser(user);
        if (!errors.isEmpty()) {
            String combinedErrors = String.join(" ", errors.values());
            throw new IllegalArgumentException(combinedErrors);
        }

        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }

    public User loginUser(String email, String password) {
        User user = findByEmail(email);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return user;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public Map<String, String> validateUser(User user) {
        Map<String, String> errors = new HashMap<>();

        if (!isValidName(user.getName())) {
            errors.put("name", "Name must be letters only and at least 3 characters long!");
        }
        if (isEmailTaken(user.getEmail())) {
            errors.put("email", "Email already exists!");
        }
        if (!isValidPassword(user.getPassword())) {
            errors.put("password", "Password must be at least 8 characters");
        }
        if (!passwordsMatch(user)) {
            errors.put("confirm", "Passwords don't match");
        }
        if (!isValidEmailFormat(user.getEmail())) {
            errors.put("email", "Invalid email format, try 'example@email.com'");
        }

        return errors;
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z\\s]+$", name) && name.length() >= 3;
    }

    private boolean isEmailTaken(String email) {
        return findByEmail(email) != null;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private boolean passwordsMatch(User user) {
        return user.getPassword().equals(user.getConfirm());
    }

    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }
}

