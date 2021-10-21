package com.olms.dto;

import com.olms.entity.BooksRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StudentDTO {

    private long id;
    private String locationInTheLibrary;
    private String studentNumber;
    private String titleOfTheBook;
    private String studentName;
    private long publicationYear;
    private String author;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String issueDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String receiveDate;
    private Double bookFine;
    private long bookId;
    private BooksRole booksRole;
    private long numbering;
}
