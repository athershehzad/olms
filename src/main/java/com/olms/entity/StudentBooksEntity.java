package com.olms.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "student_books")
@EntityListeners(AuditingEntityListener.class)
public class StudentBooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "book_id")
    private long bookId;

    @Column(name = "title_of_the_book")
    private String titleOfTheBook;

    @Column(name = "location_in_the_library")
    private String locationInTheLibrary;

    @Column(name = "publication_Year")
    private long publicationYear;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "author_of_the_book")
    private String author;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "status")
    private String status;

    @Column(name = "book_received")
    private boolean bookReceived;

    @Column(name = "issue_on")
    private LocalDate issueOn;

    @Column(name = "receive_on")
    private LocalDate receiveOn;

    @Column(name = "book_fine")
    private Double bookFine;

    @Column(name = "number_of_copies")
    private long numberings;


}
