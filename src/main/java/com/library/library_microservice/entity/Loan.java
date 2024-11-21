package com.library.library_microservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    /**
     * @onetoone is required criteria for issuing only one book per person but
     * used this annotation as when loan status=RETURNED Then  book_id can repeat.handled in loan servic logic
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // Each loan is associated with one book

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // Each loan is associated with one member
    @Column
    @CreationTimestamp
    private LocalDate loanDate;
    @Column
    private LocalDate returnDate;
    @Column
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public enum LoanStatus {
        ISSUED,
        RETURNED,
        OVERDUE
    }
}
