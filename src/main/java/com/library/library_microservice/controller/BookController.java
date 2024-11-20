package com.library.library_microservice.controller;

import com.library.library_microservice.constants.Constants;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.dto.ResponseDto;
import com.library.library_microservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "Book Management API",
        description = "REST APIs for managing books: Create, Delete, Update, and Fetch data"
)
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "API to create a new book"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": \"201\", \"message\": \"Book created successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"500\", \"message\": \"An unexpected error occurred\" }"
                            )
                    )
            )
    })
    @PostMapping(path = "/add_book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> addBook(@RequestBody BookDto bookDto) {
        bookService.addBook(bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(Constants.STATUS_201)
                        .message(Constants.MESSAGE_201)
                        .build());
    }
}
