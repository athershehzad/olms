package com.olms.dto;

import com.olms.entity.BooksRole;
import lombok.Data;

@Data
public class BooksDTO {

    private long id;
    private String author;
    private String studentNumber;
    private String titleOfTheBook;
    private String publishedPlace;
    private Long publicationYear;
    private String locationInTheLibrary;
    private Long numberings;
    private String currentStatus;
    private boolean status;
    private String search;
    private String searchType;
    private String userMessage;
    private BooksRole booksRole;
    private String roleName;
}
