package com.library.library_microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.library_microservice.Mapper.BookMapper;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.repository.BookRepo;
import com.library.library_microservice.service.BookService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private BookRepo bookRepo;

    @Test
    public void givenBookObject_whenAddBook_thenReturnHttpStatusCreated() throws Exception {

        // Given - precondition or setup
        BookDto bookDto = BookDto.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
//                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        Book book = Book.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();
        BDDMockito.given(bookMapper.toBook(bookDto)).willReturn(book);
        BDDMockito.given(bookService.addBook(bookDto))
                .willAnswer(invocation -> invocation.getArgument(0));
        //        when- action or a behaviour that we are going to test

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/add_book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookDto))
        );

        //        then- verify the output

        response.andExpect(MockMvcResultMatchers.status().isCreated());
        /*.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(book.getTitle())));*/


    }

    @Test
    public void givenBookObjects_whenGetAllBooks_thenReturnBooks() throws Exception {

        //given -precondition or setup
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);

        Book bookEntity1 = Book.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();
        Book bookEntity2 = Book.builder()
                .id(2)
                .genre(Book.Genre.BIOGRAPHY)
                .author("ram")
                .title("The monk")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        BookDto bookDto1 = BookDto.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();
        BookDto bookDto2 = BookDto.builder()
                .id(2)
                .genre(Book.Genre.BIOGRAPHY)
                .author("ram")
                .title("The monk")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();
        List<Book> bookList = Arrays.asList(bookEntity1, bookEntity2);
        Page<Book> pagedBookList = new PageImpl<>(bookList, Pageable.unpaged(), bookList.size());

        List<BookDto> bookDtos = Arrays.asList(bookDto1, bookDto2);
        Page<BookDto> pagedBookListDtos = new PageImpl<>(bookDtos, Pageable.unpaged(), bookDtos.size());

        BDDMockito.given(bookMapper.toBookDto(pagedBookList)).willReturn(pagedBookListDtos);
        BDDMockito.given(bookRepo.findAll(pageable)).willReturn(pagedBookList);
        BDDMockito.given(bookService.getAllBooks(offset, limit)).willReturn(pagedBookListDtos);

        //        when- action or a behaviour that we are going to test

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/getAllBooks")
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit))
                .contentType(MediaType.APPLICATION_JSON_VALUE));


        //        then- verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath(""));
    }
}
