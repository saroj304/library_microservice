package com.library.library_microservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Filter criteria for searching books")
public class Filter {

    @Schema(description = "The criteria to filter by")
    private Criteria criteria;

    @Schema(description = "The value associated with the criteria (e.g., genre name, author name, etc.)", example = "Fiction")
    private String criteriaValue;

    public enum Criteria {
        @Schema(description = "Filter by book genre")
        GENRE,

        @Schema(description = "Filter by book author")
        AUTHOR,

        @Schema(description = "Filter by book title")
        TITLE,

        @Schema(description = "Filter by book status (e.g., Available, Unavailable)")
        STATUS,

        @Schema(description = "Filter by book published date")
        PUBLISHED_DATE
    }

}
