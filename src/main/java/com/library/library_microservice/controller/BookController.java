package com.library.library_microservice.controller;

import com.library.library_microservice.constants.Constants;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.dto.CustomPageResponse;
import com.library.library_microservice.dto.Filter;
import com.library.library_microservice.dto.ResponseDto;
import com.library.library_microservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import net.bull.javamelody.MonitoredWithSpring;
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
        name = "Book Management API",
        description = "REST APIs for managing books: Create, Delete, Update, and Fetch data"
)
@MonitoredWithSpring
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
    @PostMapping(path = "/add_book")
    public ResponseEntity<ResponseDto> addBook(@Valid @RequestBody BookDto bookDto) {
        bookService.addBook(bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(Constants.STATUS_201)
                        .message(Constants.MESSAGE_201)
                        .build());
    }

    @Operation(
            summary = "API to UPDATE the STATUS of a BOOK"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": \"200\", \"message\": \"Status updated successfully\" }"
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

    @PutMapping(path = "/{id}/update_availability_status/{status}")
    public ResponseEntity<ResponseDto> updateBookAvailabilityStatus(
            @NotEmpty(message = "id cannot be null or empty") @PathVariable int id, @NotEmpty(message = "book status cannot be null or empty") @PathVariable String status) {

        boolean isUpdated = bookService.updateBookAvailabilityStatus(id, status);
        if (!isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                            .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .build());

        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .statusCode(Constants.STATUS_200)
                        .message(Constants.MESSAGE_200)
                        .build());
    }

    @Operation(
            summary = "Filter books based on various criteria",
            description = "Allows filtering books by genre, author, title, status, or published date. Returns paginated results."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Books fetched successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{ \"statusCode\": 200, \"message\": \"Books fetched successfully\", \"content\": [...] }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No books found matching the criteria",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": 404, \"message\": \"No books found\" }"
                            )
                    )
            )
    })
    @GetMapping("/books/filter")
    public ResponseEntity<CustomPageResponse<BookDto>> filterBooks(
            @RequestBody Filter filter,
            @Parameter(description = "Page number, starts from 0", example = "0")
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "Number of  page", example = "5")
            @RequestParam(defaultValue = "5") int limit
    ) {
        Page<BookDto> filteredBooks = bookService.filterBooks(offset, limit, filter);
        CustomPageResponse<BookDto> response = new CustomPageResponse<>(filteredBooks);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Operation(
            summary = "Fetch book details by ISBN",
            description = "This API retrieves detailed information about a book based on the provided ISBN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched book details",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "{\n  \"id\": \"1\",\n  \"title\": \"Steve Jobs\",\n  \"author\": \"Walter Isaacson\",\n  \"isbn\": \"9781451648546\",\n  \"publishedDate\": \"2011-10-24\",\n  \"description\": \"Biography of Steve Jobs\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found for the provided ISBN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"404\", \"message\": \"Book not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookDetails(@NotEmpty(message = "id cannot be null or empty") @PathVariable String isbn) {
        BookDto fetchedBookDetails = bookService.fetchBookDetails(isbn);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fetchedBookDetails);
    }


    @Operation(summary = "Get All Books", description = "Fetch a paginated list of books from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Books retrieved successfully",
                    content = @Content(mediaType = "application/json"
                    )),
            @ApiResponse(responseCode = "404",
                    description = "No books found in the database",
                    content = @Content(mediaType = "application/json"
                    ))
    })
    @GetMapping("/getAllBooks")
    public ResponseEntity<CustomPageResponse<BookDto>> getAllBooks(@NotEmpty(message = "id cannot be null or empty") @RequestParam int offset,
                                                                   @NotEmpty(message = "id cannot be null or empty") @RequestParam int limit) {
        Page<BookDto> listOfBooks = bookService.getAllBooks(offset, limit);
        CustomPageResponse<BookDto> response = new CustomPageResponse<>(listOfBooks);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/updateBook")
    public ResponseEntity<ResponseDto> updateBook(@Valid @RequestBody BookDto bookDto) {
        boolean updatedResult = bookService.updateBook(bookDto);

        if (updatedResult) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto
                            .builder()
                            .statusCode(Constants.STATUS_200)
                            .message(Constants.MESSAGE_200)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseDto
                        .builder()
                        .statusCode(Constants.STATUS_404)
                        .message(Constants.MESSAGE_404)
                        .build());
    }

}