package com.library.library_microservice.repository;

import com.library.library_microservice.entity.Book;
import com.library.library_microservice.entity.Loan;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Integer> {
    Optional<Loan> findLoanByBook(Optional<Book> fetchedBook);

    @Query("SELECT l FROM Loan l WHERE l.status = 'ISSUED' AND l.returnDate < :today")
    List<Loan> findOverdueLoans(LocalDate today);

    @Modifying
    @Query("UPDATE Loan l SET l.status = :status WHERE l.id = :id")
    @Transactional
    int updateLoanByIdAndStatus(@Param("id") int id, @Param("status") Loan.LoanStatus status);

    @Query(
            value = "SELECT * FROM loan WHERE status = :status",
            countQuery = "SELECT COUNT(*) FROM loan WHERE status = :status",
            nativeQuery = true
    )
    Page<Loan> getOverDueLoans(Pageable pageable, @Param("status") String status);}
