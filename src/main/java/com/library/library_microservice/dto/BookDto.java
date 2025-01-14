package com.library.library_microservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    @Schema(

            description = "Id number  of the book",
            example = "1,2 etc."
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Book.BookAvailabilityStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(hidden = true)
    private String subtitle;
    @Schema(

            description = "Name of the publisher",
            example = "saroj khatiwada",
            hidden = true
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String publisher;
    @Schema(

            description = "detail description of the book",
            example = "Set in the Roaring Twenties, The Great Gatsby follows the mysterious Jay Gatsby as he throws lavish parties in hopes of reuniting with his lost love, Daisy Buchanan. Through the eyes of narrator Nick Carraway, the novel explores themes of love, wealth, and the American Dream. Gatsby’s obsession with the past leads to tragic consequences, making this a poignant exploration of longing, deception, and the emptiness of material success. The novel is a timeless critique of the pursuit of happiness through wealth and status.",
            hidden = true
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @Schema(
            description = "unique identifier of the book",
            hidden = true
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isbn;


}
