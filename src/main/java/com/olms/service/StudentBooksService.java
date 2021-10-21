package com.olms.service;

import com.olms.dto.StudentDTO;
import com.olms.entity.BooksEntity;
import com.olms.entity.Role;
import com.olms.entity.StudentBooksEntity;
import com.olms.repository.BooksRepository;
import com.olms.repository.StudentBooksRepository;
import com.olms.repository.specification.BookSearchSpecification;
import com.olms.util.BookStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentBooksService {

    private final StudentBooksRepository studentBooksRepository;
    private final BooksRepository booksRepository;


    public String createUserBooks(StudentDTO studentDTO) {


        List<StudentBooksEntity> bookAssignedList = studentBooksRepository.
                findByStudentNumberAndStatusAndTitleOfTheBookAndAuthorAndPublicationYear(studentDTO.getStudentNumber(),
                BookStatus.ISSUED.name(), studentDTO.getTitleOfTheBook(), studentDTO.getAuthor(),
                        studentDTO.getPublicationYear());

        if(bookAssignedList != null && bookAssignedList.size() > 0) {
            return "<p style=\"color:red\">This book is already issued to this user.</p>";
        }


        List<StudentBooksEntity> list = studentBooksRepository.findByStudentNumberAndStatus(studentDTO.getStudentNumber(),
                BookStatus.ISSUED.name());

        if (list.size() >= 2 && studentDTO.getBooksRole().name().equals(Role.STUDENT.name())) {
            return "<p style=\"color:red\">Two books are already issued</p>";
        } else if (list.size() >= 4 && studentDTO.getBooksRole().name().equals(Role.FACULTY.name())) {
            return "<p style=\"color:red\">Four books are already issued</p>";
        } else {
            StudentBooksEntity booksEntity = new StudentBooksEntity();
            booksEntity.setTitleOfTheBook(studentDTO.getTitleOfTheBook());
            booksEntity.setStudentName(studentDTO.getStudentName());
            booksEntity.setStudentNumber(studentDTO.getStudentNumber());
            booksEntity.setLocationInTheLibrary(studentDTO.getLocationInTheLibrary());
            booksEntity.setIssueOn(LocalDate.now());
            booksEntity.setBookId(studentDTO.getBookId());
            booksEntity.setStatus(BookStatus.ISSUED.name());
            studentBooksRepository.save(booksEntity);
            BooksEntity entity = booksRepository.findById(studentDTO.getBookId()).get();
            entity.setNumberings(entity.getNumberings() - 1);
            booksRepository.save(entity);
        }
        return "updated successfully";
    }

    @Transactional
    public void updateStudentsBooks(long id, long bookId) {
        BooksEntity entity = booksRepository.findById(bookId).get();
        entity.setNumberings(entity.getNumberings() + 1);

        StudentBooksEntity booksEntity = studentBooksRepository.findById(id).get();
        booksEntity.setBookReceived(true);
        booksEntity.setReceiveOn(LocalDate.now());
        booksEntity.setStatus(BookStatus.RECEIVED.name());
        booksEntity.setNumberings(entity.getNumberings());
        booksEntity.setTitleOfTheBook(entity.getTitleOfTheBook());
        booksEntity.setAuthor(entity.getAuthor());
        booksEntity.setPublicationYear(entity.getPublicationYear());
        studentBooksRepository.save(booksEntity);
        booksRepository.save(entity);
    }

    public List<StudentDTO> searchStudents(String studentNumber) {
        List<StudentBooksEntity> list = studentBooksRepository.findByStudentNumberAndStatus(studentNumber, BookStatus.ISSUED.name());
        StudentDTO studentDTO;
        List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
        for (StudentBooksEntity booksEntity : list) {
            studentDTO = new StudentDTO();
            studentDTO.setLocationInTheLibrary(booksEntity.getLocationInTheLibrary());
            studentDTO.setId(booksEntity.getId());
            studentDTO.setStudentName(booksEntity.getStudentName());
            studentDTO.setStudentNumber(booksEntity.getStudentNumber());
            studentDTO.setTitleOfTheBook(booksEntity.getTitleOfTheBook());
            studentDTO.setBookFine(booksEntity.getBookFine());
            studentDTO.setBookId(booksEntity.getBookId());
            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }

    public Page<StudentBooksEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
                                                  StudentDTO studentDTO) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.studentBooksRepository.findAll(new BookSearchSpecification(studentDTO), pageable);


    }

}