package com.library.library_microservice.repository;

import com.library.library_microservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByTitleAndAuthorAndGenre(String title, String author, Book.Genre genre);
}
