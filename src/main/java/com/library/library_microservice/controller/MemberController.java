package com.library.library_microservice.controller;

import com.library.library_microservice.constants.Constants;
import com.library.library_microservice.dto.MemberDto;
import com.library.library_microservice.dto.ResponseDto;
import com.library.library_microservice.service.MemberService;
import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
        name = "Member creation API",
        description = "REST APIs for managing members: Create, Delete, Update, and Fetch member"
)
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "API to create a new Member"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Member created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
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
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "ErrorResponse",
                                    value = "{ \"statusCode\": \"500\", \"message\": \"An unexpected error occurred\" }"
                            )
                    )
            )
    })
    @PostMapping("/create_member")
    public ResponseEntity<ResponseDto> createMember(@Valid @RequestBody MemberDto memberDto) {
        memberService.createMember(memberDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(Constants.STATUS_201)
                        .message(Constants.MESSAGE_201)
                        .build());
    }
}
