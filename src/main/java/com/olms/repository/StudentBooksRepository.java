package com.olms.repository;

import com.olms.entity.StudentBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentBooksRepository extends JpaRepository<StudentBooksEntity, Long>,
        JpaSpecificationExecutor<StudentBooksEntity> {

    List<StudentBooksEntity> findByStudentNumberAndStatus(String studentNumber, String bookStatus);

    @Query(value = "select * from olms.student_books sb " +
            "WHERE sb.status = 'ISSUED' and  sb.issue_on < NOW() - INTERVAL 15 DAY",
            nativeQuery = true)
    List<StudentBooksEntity> findByIssueOnIsGreaterThan();

    List<StudentBooksEntity> findByStudentNumberAndStatusAndTitleOfTheBookAndAuthorAndPublicationYear(String studentNumber,
                                                                                             String bookStatus,
                                                                               String titleOfTheBook,
                                                          String author, long publicationYear);
}


