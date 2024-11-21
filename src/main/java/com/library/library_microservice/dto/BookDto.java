package com.library.library_microservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.library_microservice.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author - Saroj khatiwada
 * @date - 11/20/2024
 */
@Schema(
        name = "Book",
        description = "holds the Book  information"
)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
public class BookDto{
    @NotEmpty(message = "title name cannot be null or empty")
    @Schema(

            description = "title name  of the book"
    )
    private String title;
    @NotEmpty(message = "author name cannot be null or empty")
    @Schema(
            description = "author of the book"
    )
    private String author;
    @NotEmpty(message = "Genre cannot be null or empty")
    @Schema(

            description = "Genre on which the book belong"
    )
    private Book.Genre genre;
    @NotEmpty(message = "published Date cannot be null or empty")
    @Schema(

            description = "Date of publishment"
    )
    private LocalDate publishedDate;

    @Schema(

            description = "BookAvailability Status ",
            example = "AVAILABLE,UNAVAILABLE"
    )
    @Enumerated(EnumType.STRING)
    private Book.BookAvailabilityStatus status;

    private String subtitle;
    private String publisher;
    private String description;
    private String isbn;


}
