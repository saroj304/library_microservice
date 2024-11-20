package com.library.library_microservice.Mapper;

import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toBook(BookDto bookDto) {
        return Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .genre(bookDto.getGenre())
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(bookDto.getPublishedDate())
                .build();

    }
}
