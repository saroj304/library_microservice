package com.library.library_microservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column
    private LocalDate publishedDate;
    @Enumerated(EnumType.STRING)
    @Column
    private BookAvailabilityStatus status;

    public enum BookAvailabilityStatus {
        AVAILABLE,
        UNAVAILABLE
    }
    public enum Genre {
        FICTION,
        NON_FICTION,
        MYSTERY,
        FANTASY,
        SCIENCE_FICTION,
        BIOGRAPHY,
        HISTORY,
        ROMANCE,
        THRILLER,
    }
}

