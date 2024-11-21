package com.library.library_microservice.service.impl;

import com.library.library_microservice.Mapper.LoanMapper;
import com.library.library_microservice.dto.LoanDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.entity.Loan;
import com.library.library_microservice.entity.Member;
import com.library.library_microservice.exception.AlreadyExistsException;
import com.library.library_microservice.exception.ResourceNotFoundException;
import com.library.library_microservice.repository.BookRepo;
import com.library.library_microservice.repository.LoanRepo;
import com.library.library_microservice.repository.MemberRepo;
import com.library.library_microservice.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepo loanRepo;
    private final LoanMapper loanMapper;
    private final BookRepo bookRepo;
    private final MemberRepo memberRepo;

    @Override
    /**
     *  before creating loan check if book and member exists  and check whether the loan was already issued
     *  with that particular book since one member can get muliple books but one books cannot be assigned to multiple member
     * if loanStatus is issued or overdue then throw error
     *  also check whether the due date of the book is beyond 7 days
     */
    public Loan issueBook(LoanDto loanDto) {

        Optional<Book> fetchedBook = Optional.ofNullable(bookRepo.findById(loanDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with the id " + loanDto.getBookId() + " does not exist"))
        );

        Optional<Member> fetchedMember = Optional.ofNullable(memberRepo.findById(loanDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member with the id " + loanDto.getMemberId() + " does not exist"))
        );

        verifyBookAvailability(loanDto, fetchedBook, fetchedMember);

        Optional<Loan> fetchedLoan = loanRepo.findLoanByBook(fetchedBook);

        if (fetchedLoan.isPresent() && (fetchedLoan.get().getStatus().name().equals("ISSUED")
                || fetchedLoan.get().getStatus().name().equals("OVERDUE"))) {
            throw new AlreadyExistsException("Loan was already issued with the book id" +
                    fetchedBook.get().getId() + " and the loan status is" + fetchedLoan.get().getStatus().name());
        }
        isReturnDateValid(loanDto.getReturnDate());
        Loan loan = loanMapper.toLoan(loanDto, fetchedBook, fetchedMember);
        return loanRepo.save(loan);
    }

    @Override
    public void updateOverdueLoans() {
        LocalDate today = LocalDate.now();

        // Fetch loans where returnDate has passed
        List<Loan> overdueLoans = loanRepo.findOverdueLoans(today);

        for (Loan loan : overdueLoans) {
            loan.setStatus(Loan.LoanStatus.OVERDUE);
        }

        // Save the updated loans
        loanRepo.saveAll(overdueLoans);
    }

    @Override
    public boolean returnBook(LoanDto loanDto) {
        loanDto.setReturnDate(LocalDate.now());
        Optional<Loan> fetchedLoan = getLoanByBook(loanDto);

        if (fetchedLoan.isPresent()) {
            if (fetchedLoan.get().getStatus().name().equals("RETURNED")) {
                throw new AlreadyExistsException("Book  was already RETURNED");
            }
            if (fetchedLoan.get().getStatus().name().equals("OVERDUE")) {
                throw new AlreadyExistsException("Book  was already OVERDUE");
            }

            if (fetchedLoan.get().getReturnDate().isBefore(loanDto.getReturnDate()) && fetchedLoan.get().getStatus() == Loan.LoanStatus.ISSUED) {
                loanRepo.updateLoanByIdAndStatus(fetchedLoan.get().getId(), Loan.LoanStatus.OVERDUE);

            } else {
                loanRepo.updateLoanByIdAndStatus(fetchedLoan.get().getId(), Loan.LoanStatus.RETURNED);

            }
            return true;
        }
        return false;
    }

    @Override
    public Page<LoanDto> getOverDueLoans(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Loan> overDueLoans = loanRepo.getOverDueLoans(pageable, Loan.LoanStatus.OVERDUE.name());
        if (overDueLoans.isEmpty()) {
            throw new ResourceNotFoundException("OverDue loans not found");
        }
        return loanMapper.toLoanDto(overDueLoans);
    }

    private Optional<Loan> getLoanByBook(LoanDto loanDto) {
        Optional<Book> fetchedBook = Optional.ofNullable(bookRepo.findById(loanDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with the id " + loanDto.getBookId() + " does not exist"))
        );

        Optional<Member> fetchedMember = Optional.ofNullable(memberRepo.findById(loanDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member with the id " + loanDto.getMemberId() + " does not exist"))
        );
        verifyBookAvailability(loanDto, fetchedBook, fetchedMember);
        return loanRepo.findLoanByBook(fetchedBook);
    }

    private void verifyBookAvailability(LoanDto loanDto, Optional<Book> fetchedBook, Optional<Member> fetchedMember) {

        if (fetchedBook.get().getStatus().name().equals("UNAVAILABLE")) {
            throw new ResourceNotFoundException("Book with the id " + loanDto.getBookId() + " is not available");
        }
    }

    public void isReturnDateValid(LocalDate returnDate) {
        LocalDate today = LocalDate.now();
        boolean result = !returnDate.isAfter(today.plusDays(7));
        if (!result) {
            throw new RuntimeException("return Date must be with in 7 day of  issue Date");
        }
    }
}
