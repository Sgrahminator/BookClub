package com.sarahgraham.bookclub.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sarahgraham.bookclub.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    ArrayList<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id); 
}
