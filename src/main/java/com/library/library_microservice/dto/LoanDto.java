package com.library.library_microservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;

/**
 * @author - Saroj khatiwada
 * @date - 11/20/2024
 */


@Schema(
        name = "Loan",
        description = "holds the Member and book information for issuing a book"
)

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoanDto {
    @NotEmpty(message = "member id  cannot be null or empty")
    @Schema(description = "Member ID to whom the book is issued")
    private int memberId;

    @NotEmpty(message = "bookId  cannot be null or empty")
    @Schema(description = "Book ID issued to the member")
    private int bookId;

    @NotEmpty(message = "return Date cannot be null or empty")

    @Schema(description = "Due date for returning the book")
    private LocalDate returnDate;

}
