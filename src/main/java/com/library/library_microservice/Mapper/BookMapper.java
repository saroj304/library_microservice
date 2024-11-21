package com.library.library_microservice.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;
import io.micrometer.common.util.StringUtils;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    public Page<BookDto> toBookDto(Page<Book> filteredBooks) {
        return filteredBooks
                .map(filteredBook ->
                        BookDto.builder()
                                .title(filteredBook.getTitle())
                                .author(filteredBook.getAuthor())
                                .genre(filteredBook.getGenre())
                                .publishedDate(filteredBook.getPublishedDate())
                                .status(filteredBook.getStatus())
                                .build());

    }

    public BookDto toBookDtoFromJsonNode(JsonNode volumeInfoNode, String isbn) {
        String authorName = volumeInfoNode.path("authors").get(0).asText();
        LocalDate publishedDate = LocalDate.parse(volumeInfoNode.path("publishedDate").asText());
        String title = volumeInfoNode.path("title").asText();
        String subtitle = volumeInfoNode.path("subtitle").asText();
        String genre = volumeInfoNode.path("genre").asText();
        String publisher = volumeInfoNode.path("publisher").asText();
        String description = volumeInfoNode.path("description").asText();

        return BookDto.builder()
                .title(title)
                .author(authorName)
                .subtitle(subtitle)
                .publishedDate(publishedDate)
                .publisher(publisher)
                .description(description)
                .isbn(isbn)
                .build();
    }
}
