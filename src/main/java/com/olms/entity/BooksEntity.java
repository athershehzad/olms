package com.olms.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "author_of_the_book")
    private String author;

    @Column(name = "title_of_the_book")
    private String titleOfTheBook;

    @Column(name = "Published_place")
    private String publishedPlace;

    @Column(name = "publication_Year")
    private long publicationYear;

    @Column(name = "location_in_the_library")
    private String locationInTheLibrary;

    @Column(name = "number_of_copies")
    private long numberings;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "status")
    private boolean status;

    @Column(name = "created_by")
    @CreatedDate
    private Date createdBy;

    @Column(name = "updated_by")
    @CreatedBy
    private String updatedBy;
}
