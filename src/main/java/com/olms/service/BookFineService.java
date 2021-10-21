package com.olms.service;

import com.olms.entity.BookFineEntity;
import com.olms.exceptions.NotFoundException;
import com.olms.repository.BookFineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookFineService {

    private final BookFineRepository bookFineRepository;

    public BookFineService(BookFineRepository userInfoRepository) {
        this.bookFineRepository = userInfoRepository;
    }

    public BookFineEntity getFine(Integer id) {
        return bookFineRepository.findByIdAndStatus(id, true).orElseThrow(NotFoundException::new);
    }

    public BookFineEntity createFine(BookFineEntity entity) {

        BookFineEntity bookFine = new BookFineEntity();
        bookFine.setStatus(true);
        bookFine.setFineAmount(entity.getFineAmount());
        bookFine.setCreatedOn(LocalDate.now());
        return bookFineRepository.save(bookFine);
    }

    public List<BookFineEntity> getBookFine() {
        return bookFineRepository.findAllByStatusOrderByIdDesc(true);
    }

    public BookFineEntity updateFine(Integer id, BookFineEntity request) {
        BookFineEntity fromDb = getFine(id);
        fromDb.setFineAmount(request.getFineAmount());
        fromDb.setStatus(request.isStatus());
        fromDb.setUpdatedOn(LocalDate.now());
        return bookFineRepository.save(fromDb);
    }
}
