package com.library.library_microservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response DTO for all APIs")
public class ResponseDto {
    @Schema(description = "HTTP status code", example = "201")
    private String statusCode;

    @Schema(description = "Response message", example = "created successfully")
    private String message;
}
