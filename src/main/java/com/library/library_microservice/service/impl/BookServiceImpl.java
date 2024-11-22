package com.library.library_microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.library_microservice.Mapper.BookMapper;
import com.library.library_microservice.dto.BookDto;
import com.library.library_microservice.dto.Filter;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.exception.AlreadyExistsException;
import com.library.library_microservice.exception.ResourceNotFoundException;
import com.library.library_microservice.external_api.GoogleBooksClient;
import com.library.library_microservice.repository.BookRepo;
import com.library.library_microservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final GoogleBooksClient feignClient;
    private Environment environment;

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

    @Override
    public boolean updateBookAvailabilityStatus(int id, String status) {
        bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        int updateRows = bookRepo.updateBookByIdAndStatus(id, Book.BookAvailabilityStatus.valueOf(status));
        return updateRows > 0;

    }

    @Override
    public Page<BookDto> filterBooks(int offset, int limit, Filter filter) {
        Pageable pageable = PageRequest.of(offset, limit);

        Specification<Book> spec = Specification.where(null);

        if (filter.getCriteria() != null) {
            switch (filter.getCriteria()) {
                case GENRE:
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("genre"), filter.getCriteriaValue()));
                    break;
                case AUTHOR:
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("author"), filter.getCriteriaValue()));
                    break;
                case TITLE:
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("title"), filter.getCriteriaValue()));
                    break;
                case STATUS:
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("status"), filter.getCriteriaValue()));
                    break;
                case PUBLISHED_DATE:
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("publishedDate"), LocalDate.parse(filter.getCriteriaValue())));
                    break;
                default:
                    break;
            }
        }
        Page<Book> filteredBooks = bookRepo.findAll(spec, pageable);
        if (!filteredBooks.hasContent()) {
            throw new ResourceNotFoundException("There is no book record exists");
        }
        return bookMapper.toBookDto(filteredBooks);
    }

    @Override
    public BookDto fetchBookDetails(String isbn) {
        // Retrieve the API key from environment properties
        String apiKey = environment.getProperty("google.books.api.key");

        // Fetch data from the Feign client
        JsonNode data = feignClient.getBookByIsbn(isbn, apiKey);


        if (data == null || data.isEmpty()) {
            throw new ResourceNotFoundException("No data found for ISBN: " + isbn);
        }

        // If you need to extract the "items" array and deserialize into BookDto
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode itemsNode = data.path("items");  // Get the "items" node
        JsonNode firstItemInList = itemsNode.get(0);

        JsonNode volumeInfoNode = firstItemInList.path("volumeInfo");


        return bookMapper.toBookDtoFromJsonNode(volumeInfoNode, isbn);


    }

    @Override
    public Page<BookDto> getAllBooks(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);

        Page<Book> books = bookRepo.findAll(pageable);
        if (!books.hasContent()) {
            throw new ResourceNotFoundException("There is no book record exists");
        }
        return bookMapper.toBookDto(books);

    }

    @Override
    public boolean updateBook(BookDto bookDto) {
        boolean isUpdated = true;
        Optional<Book> bookEntity = bookRepo.findById(bookDto.getId());
        if (bookEntity.isPresent()) {

            bookRepo.save(bookMapper.toBook(bookDto));
            return  isUpdated;
        }
        return !isUpdated;
    }
}




