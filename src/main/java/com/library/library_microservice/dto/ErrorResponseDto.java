package com.library.library_microservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(
        name = "error response",
        description = "schema to hold the error response "
)
public class ErrorResponseDto {
    @Schema(
            description = "path on which the error has occured", example = "api/user/createBook"
    )
    private String apiPath;
    @Schema(
            description = "error code ", example = "500"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "error Message", example = "Internal Server Error"
    )
    private String errorMessage;
    @Schema(
            description = "time stamp of the error"
    )
    private LocalDateTime errorDateTime;
}
