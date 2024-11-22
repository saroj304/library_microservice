package com.library.library_microservice.controller;

import com.library.library_microservice.constants.Constants;
import com.library.library_microservice.dto.CustomPageResponse;
import com.library.library_microservice.dto.LoanDto;
import com.library.library_microservice.dto.ResponseDto;
import com.library.library_microservice.repository.LoanRepo;
import com.library.library_microservice.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "Loan Management API",
        description = "REST APIs for managing books: Create, Delete, Update, and Fetch data"
)
public class LoanController {
    private final LoanService loanService;
    private final LoanRepo loanRepo;

    @Operation(
            summary = "API to  issue a loan"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Member created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": \"201\", \"message\": \"Member created successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"500\", \"message\": \"An unexpected error occurred\" }"
                            )
                    )
            )
    })
    @PostMapping("/issue_book")
    public ResponseEntity<ResponseDto> issueBook(@Valid @RequestBody LoanDto loanDto) {
        loanService.issueBook(loanDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(Constants.STATUS_201)
                        .message(Constants.MESSAGE_201)
                        .build());
    }

    @Operation(summary = "API to return a book")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": \"200\", \"message\": \"Book returned successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"404\", \"message\": \"The requested loan was not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/return_book")
    public ResponseEntity<ResponseDto> returnBook(@Valid @RequestBody LoanDto loanDto) {

        boolean result = loanService.returnBook(loanDto);
        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseDto.builder()
                            .statusCode(Constants.STATUS_404)
                            .message(Constants.MESSAGE_404)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(Constants.STATUS_200)
                        .message(Constants.MESSAGE_200)
                        .build());
    }

    @Operation(summary = "API to fetch overdue loans")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": \"200\", \"message\": \"Overdue loans fetched successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"404\", \"message\": \"No overdue loans found\" }"
                            )
                    )
            )
    })
    @GetMapping("/overDue_loans")
    public ResponseEntity<CustomPageResponse<LoanDto>> getOverDueLoans(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit
    ) {
        Page<LoanDto> overDueLoans = loanService.getOverDueLoans(offset, limit);
        CustomPageResponse<LoanDto> response = new CustomPageResponse<>(overDueLoans);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
