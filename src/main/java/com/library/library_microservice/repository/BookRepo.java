package com.library.library_microservice.repository;

import com.library.library_microservice.entity.Book;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByTitleAndAuthorAndGenre(String title, String author, Book.Genre genre);
    @Modifying
    @Query("UPDATE Book b SET b.status = :status WHERE b.id = :id")
    @Transactional
    int updateBookByIdAndStatus(@Param("id") int id, @Param("status") Book.BookAvailabilityStatus status);


    Page<Book> findAll(Specification<Book> spec, Pageable pageable);
}
