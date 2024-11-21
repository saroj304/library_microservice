package com.library.library_microservice.service;

import com.library.library_microservice.dto.LoanDto;
import com.library.library_microservice.entity.Loan;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface LoanService {
    Loan issueBook(LoanDto loanDto);

    void updateOverdueLoans();

    boolean returnBook(@Valid LoanDto loanDto);

    Page<LoanDto> getOverDueLoans(int offset, int limit);
}
