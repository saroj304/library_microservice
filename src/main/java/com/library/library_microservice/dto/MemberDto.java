package com.library.library_microservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author - Saroj khatiwada
 * @date - 11/20/2024
 */
@Schema(
        name = "Member",
        description = "Holds the Library Member information"
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    @NotEmpty(message = "name name cannot be null or empty")
    @Schema(

            description = " name  of the new Member"
    )
    private String name;
    @NotEmpty(message = "email name cannot be null or empty")
    @Schema(
            description = "email id of the member"
    )
    private String email;
}
