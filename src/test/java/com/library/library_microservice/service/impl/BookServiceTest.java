package com.library.library_microservice.service.impl;

import com.library.library_microservice.Mapper.BookMapper;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.exception.AlreadyExistsException;
import com.library.library_microservice.exception.ResourceNotFoundException;
import com.library.library_microservice.repository.BookRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    @DisplayName("jUnit test to save a book")
    public void givenBookDto_whenAddBook_thenReturnBookObject() {

        // Given - precondition or setup
        BookDto bookDto = BookDto.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        Book bookEntity = Book.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        BDDMockito.given(bookMapper.toBook(bookDto)).willReturn(bookEntity);
        BDDMockito.given(bookRepo.findBookByTitleAndAuthorAndGenre(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getGenre()))
                .willReturn(Optional.empty());
        BDDMockito.given(bookRepo.save(bookEntity)).willReturn(bookEntity);


        // When - action
        Book savedBook = bookService.addBook(bookDto);

        // Then - verify the output
        Assertions.assertThat(savedBook).isNotNull();

    }

    @Test
    @DisplayName("jUnit test to save a book")
    public void givenBookDto_whenAddBook_thenThrowBookExistsException() {

        // Given - precondition or setup
        BookDto bookDto = BookDto.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        Book bookEntity = Book.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        BDDMockito.given(bookMapper.toBook(bookDto)).willReturn(bookEntity);
        BDDMockito.given(bookRepo.findBookByTitleAndAuthorAndGenre(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getGenre()))
                .willReturn(Optional.ofNullable(bookEntity));


        // When - action
        org.junit.jupiter.api.Assertions.assertThrows(AlreadyExistsException.class, () -> {
            bookService.addBook(bookDto);
        });

        // Then - verify the output
        BDDMockito.verify(bookRepo, Mockito.never()).save(bookEntity);

    }

    @Test
    public void givenOffsetAndLimit_whenGetAllBooks_thenReturnListOfBooks() {
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

        BDDMockito.given(bookRepo.findAll(pageable)).willReturn(pagedBookList);
        BDDMockito.given(bookMapper.toBookDto(pagedBookList)).willReturn(pagedBookListDtos);

        //        when- action or a behaviour that we are going to test

        Page<BookDto> fetchedBookDtos = bookService.getAllBooks(offset, limit);

        //        then- verify the output
        org.junit.jupiter.api.Assertions.assertNotNull(fetchedBookDtos);
        org.junit.jupiter.api.Assertions.assertEquals(pagedBookListDtos, fetchedBookDtos);
        Assertions.assertThat(fetchedBookDtos.getSize()).isEqualTo(pagedBookListDtos.getSize());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if the books doesnot exists")
    public void givenOffsetAndLimit_whenGetAllBooks_thenReturnResourceNotFoundException() {
        //given -precondition or setup
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);

       /* List<BookDto> bookDtos = Arrays.asList(bookDto1, bookDto2);
        Page<BookDto> pagedBookListDtos = new PageImpl<>(bookDtos, Pageable.unpaged(), bookDtos.size());*/

        BDDMockito.given(bookRepo.findAll(pageable)).willReturn(Page.empty());
//        BDDMockito.given(bookMapper.toBookDto(pagedBookList)).willReturn(pagedBookListDtos);

        //        when- action or a behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getAllBooks(offset, limit);
        });

        //        then- verify the output
        BDDMockito.verify(bookMapper, Mockito.never()).toBookDto(Mockito.any());

    }

    @Test
    public void givenBook_whenUpdateBook_thenReturnTrue() {
        //given -precondition or setup

        Book bookEntity = Book.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1)
                .genre(Book.Genre.BIOGRAPHY)
                .author("Saroj")
                .title("The Old Man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.of(2054, 2, 27))
                .build();

        BDDMockito.given(bookRepo.findById(bookEntity.getId())).willReturn(Optional.of(bookEntity));
        BDDMockito.given(bookMapper.toBook(bookDto)).willReturn(bookEntity);
        BDDMockito.given(bookRepo.save(bookEntity)).willReturn(Mockito.any());
        bookEntity.setAuthor("ajay");
        //        when- action or a behavioue that we are going to test
        boolean result = bookService.updateBook(bookDto);
        //        then- verify the output
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(bookRepo.findById(bookEntity.getId()).get().getAuthor())
                .isEqualTo("ajay");
        // Verify that save was called with the correct entity
        BDDMockito.verify(bookRepo).save(bookEntity);

    }
}


