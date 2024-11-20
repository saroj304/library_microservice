package com.library.library_microservice.service;

import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;

public interface BookService {
    Book addBook(BookDto bookId);
}
