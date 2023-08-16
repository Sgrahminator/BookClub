package com.sarahgraham.bookclub.repositories;

import com.sarahgraham.bookclub.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
    List<Book> findByUserId(Long userId);
    List<Book> findByPostedBy(String postedBy);
    List<Book> findByUserIdAndId(Long userId, Long bookId);
}