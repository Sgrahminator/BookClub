package com.sarahgraham.bookclub.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sarahgraham.bookclub.models.Book;
import com.sarahgraham.bookclub.models.User;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    ArrayList<Book> findAll();
    Optional<Book> findByUser(User user);
}
