package com.sarahgraham.bookclub.repositories;

import com.sarahgraham.bookclub.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByIdAndEmail(Long id, String email);
    List<User> findAll();
}