package com.library.library_microservice.respository;

import com.library.library_microservice.entity.Book;
import com.library.library_microservice.repository.BookRepo;

import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepoTests {
    @Autowired
    private BookRepo bookRepo;
    private Book book;

    @BeforeEach
    public void bookSetup(){

        book = Book.builder()
                .genre(Book.Genre.BIOGRAPHY)
                .title("the old man")
                .status(Book.BookAvailabilityStatus.AVAILABLE)
                .publishedDate(LocalDate.ofEpochDay(2054 - 02 - 27))
                .build();
    }


    @DisplayName("Junit test for save book operation")
    @Test
    public void givenBookObject_whenSave_thenReturnSavedBook() {
//        given -precondition or setup
      //created generic  book in the above
//        when - action or a behaviour that we are going to test
        Book savedBook = bookRepo.save(book);
//        then- verify the output
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    //    junit test for getALl book operation
    @DisplayName("jUnit test to get list of employees")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
//        given -precondition or setup
        Book book = Book.builder()
                .genre(Book.Genre.FANTASY)
                .title("the old women")
                .publishedDate(LocalDate.ofEpochDay(2054 - 02 - 27))
                .build();
        Book book1 = Book.builder()
                .genre(Book.Genre.FANTASY)
                .title("russian love")
                .publishedDate(LocalDate.ofEpochDay(2054 - 02 - 27))
                .build();


        Book book2 = Book.builder()
                .genre(Book.Genre.SCIENCE_FICTION)
                .title(" Harry Potter and the goblet of fire")
                .publishedDate(LocalDate.ofEpochDay(2000 - 02 - 27))
                .build();

        bookRepo.save(book);
        bookRepo.save(book1);
        bookRepo.save(book2);
//        when- action or a behavioue that we are going to test

        List<Book> bookList = bookRepo.findAll();
//        then- verify the output
        assertThat(bookList).isNotNull();
        assertThat(bookList.size()).isEqualTo(3);
    }

    @DisplayName("jUnit test for getting book by id")
    @Test
    public void givenBookId_whenFindById_thenReturnBook() {
        //given -precondition or setup
        Book book2 = Book.builder()
                .genre(Book.Genre.SCIENCE_FICTION)
                .title(" Harry Potter and the goblet of fire")
                .publishedDate(LocalDate.ofEpochDay(2000 - 02 - 27))
                .build();
        bookRepo.save(book2);

        //        when- action or a behavioue that we are going to test

        Book book = bookRepo.findById(book2.getId()).get();
        //        then- verify the output

        assertThat(book).isNotNull();
        assertThat(book.getId()).isGreaterThan(0);
    }

    @Test
    public void givenBook_whenUpdate_thenReturnUpdatedBook() {
        //given -precondition or setup
        Book book = Book.builder()
                .genre(Book.Genre.SCIENCE_FICTION)
                .title(" Harry Potter and the goblet of fire")
                .publishedDate(LocalDate.ofEpochDay(2000 - 02 - 27))
                .build();
        bookRepo.save(book);

        //        when- action or a behavioue that we are going to test

        book.setAuthor("Ram");
        book.setGenre(Book.Genre.HISTORY);
        book.setTitle("Nepal");
        book.setPublishedDate(LocalDate.ofEpochDay(2000 - 02 - 27));

        Book updatedBook = bookRepo.save(book);

        //        then- verify the output

        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getId()).isEqualTo(book.getId());
    }

    @DisplayName("jUnit to delete the employee by id")
    @Test
    public void givenBookID_whenDeleteById_thenReturnEmpty() {
        //given -precondition or setup
        Book book = Book.builder()
                .genre(Book.Genre.SCIENCE_FICTION)
                .title(" Harry Potter and the goblet of fire")
                .publishedDate(LocalDate.ofEpochDay(2000 - 02 - 27))
                .build();
        bookRepo.save(book);

        //        when- action or a behavioue that we are going to test
        bookRepo.deleteById(book.getId());
        Optional<Book> bookEntity = bookRepo.findById(book.getId());
        //        then- verify the output

        assertThat(bookEntity.isPresent()).isFalse();
    }

    @Test
    public void givenIdAndStaus_whenUpdateBookByIdAndStatus_then() {
        // given -precondition or setup

        Book savedBook = bookRepo.save(book);
        System.out.println(savedBook.getId());
        //  when- action or a behavioue that we are going to test
        bookRepo.updateBookByIdAndStatus(savedBook.getId(), Book.BookAvailabilityStatus.UNAVAILABLE);
        Optional<Book> book1 = bookRepo.findById(savedBook.getId());
        System.out.println(book1.get());
        //   then- verify the output
        assertThat(book1.isPresent()).isTrue();
        System.out.println(book1.get().getStatus());
        assertThat(book1.get().getStatus().name()).isEqualTo(Book.BookAvailabilityStatus.UNAVAILABLE.name());
    }
}
