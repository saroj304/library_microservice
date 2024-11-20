package com.library.library_microservice.service.impl;

import com.library.library_microservice.Mapper.BookMapper;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.exception.AlreadyExistsException;
import com.library.library_microservice.repository.BookRepo;
import com.library.library_microservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    @Override
    public Book addBook(BookDto bookDto) {
        Book book = bookMapper.toBook(bookDto);
        Optional<Book> existingBook = bookRepo.findBookByTitleAndAuthorAndGenre(book.getTitle(), book.getAuthor(), book.getGenre());
        if (existingBook.isPresent()) {
            throw new AlreadyExistsException("Book with the title " + book.getTitle() + " author " + book.getAuthor() + "and " +
                    "Genre " + book.getGenre() + " already exists");
        }
        return bookRepo.save(book);

    }
}
