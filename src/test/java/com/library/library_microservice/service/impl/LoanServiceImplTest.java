package com.library.library_microservice.service.impl;

import com.library.library_microservice.dto.LoanDto;
import com.library.library_microservice.entity.Book;
import com.library.library_microservice.entity.Member;
import com.library.library_microservice.exception.ResourceNotFoundException;
import com.library.library_microservice.repository.BookRepo;
import com.library.library_microservice.repository.MemberRepo;
import com.library.library_microservice.service.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class LoanServiceImplTest {
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private LoanService loanService;

    public void createMember(String name, String email) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .build();
        memberRepo.save(member);
    }

    public void createBook(String bookTitle, Book.BookAvailabilityStatus status, String author, Book.Genre genre, String pulishedDate) {
        Book book = Book.builder()
                .title(bookTitle)
                .genre(genre)
                .publishedDate(LocalDate.parse(pulishedDate))
                .status(status)
                .author(author)
                .build();
        bookRepo.save(book);
    }


    private void createAndCheckIfBookCanBeIssueIfUnAvailable(String gmail, Book.BookAvailabilityStatus status, String returnDate
            , String bookTitle, String author, Book.Genre genre, String bookPublishedDate) {

        Optional<Member> member = memberRepo.findByEmail(gmail);

        createBook(bookTitle, status, author, genre, bookPublishedDate);
        Optional<Book> bookFromDb = bookRepo.findBookByTitleAndAuthorAndGenre(bookTitle, author, genre);


        LoanDto loanDto = LoanDto
                .builder()
                .bookId(bookFromDb.get().getId())
                .memberId(member.get().getId())
                .returnDate(LocalDate.parse(returnDate))
                .build();


        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            loanService.issueBook(loanDto);
        });
        // Verify exception message
        String expectedMessage = "Book with the id " + bookFromDb.get().getId() + " is not available";
        assert exception.getMessage().contains(expectedMessage);


    }


    private void createAndCheckIfBookCanBeIssueIfAvailable(String gmail, Book.BookAvailabilityStatus status, String returnDate
            , String bookTitle, String author, Book.Genre genre, String publishedDate
    ) {

        Optional<Member> member = memberRepo.findByEmail(gmail);

        createBook(bookTitle, status, author, genre, publishedDate);
        Optional<Book> bookFromDb = bookRepo.findBookByTitleAndAuthorAndGenre(bookTitle, author, genre);

        LoanDto loanDto = LoanDto
                .builder()
                .bookId(bookFromDb.get().getId())
                .memberId(member.get().getId())
                .returnDate(LocalDate.parse(returnDate))
                .build();

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            loanService.issueBook(loanDto);
        });
        // Verify exception message
        String expectedMessage = "return Date must be with in 7 day of  issue Date";
        assert exception.getMessage().contains(expectedMessage);

    }

    @Test
    void testIssueBookOnBookAvailability() {
        createMember("kalpana","kalpanakhatiwada1999@gmail.com");
        /**
         * when book return date is above 7 days and bookStatus is available
         */
        createAndCheckIfBookCanBeIssueIfAvailable("kalpanakhatiwada1999@gmail.com", Book.BookAvailabilityStatus.AVAILABLE,
                "2024-11-29", "1987", "hari", Book.Genre.FANTASY, "1949-06-01");


    }

    @Test
    void testIssueBookOnBookUnavailability() {
//        createMember();

        /** when book return date is within 7 days and bookStatus is unavailable
         */

        createAndCheckIfBookCanBeIssueIfUnAvailable("ajaywagle1999@gmail.com", Book.BookAvailabilityStatus.UNAVAILABLE
                , "2024-11-21", "1977", "ram", Book.Genre.HISTORY, "1949-06-08");

    }
}