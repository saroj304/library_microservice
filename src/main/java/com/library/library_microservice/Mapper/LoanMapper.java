package com.library.library_microservice.Mapper;

import com.library.library_microservice.dto.LoanDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.entity.Loan;
import com.library.library_microservice.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoanMapper {
    public Loan toLoan(LoanDto loanDto, Optional<Book> book, Optional<Member> member) {
        return Loan.builder()
                .book(book.get())
                .member(member.get())
                .returnDate(loanDto.getReturnDate())
                .status(Loan.LoanStatus.ISSUED)
                .build();
    }

    public Page<LoanDto> toLoanDto(Page<Loan> loans) {
        return loans.map(loan -> LoanDto.builder()
                .bookId(loan.getBook().getId())
                .memberId(loan.getMember().getId())
                .returnDate(loan.getReturnDate())
                .build()
        );
    }


}
