package com.library.library_microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.dto.Filter;
import com.library.library_microservice.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Book addBook(BookDto bookId);

    boolean updateBookAvailabilityStatus(int id,String status);

    Page<BookDto> filterBooks(int offset, int limit, Filter filter);


    BookDto fetchBookDetails(String isbn);
}
