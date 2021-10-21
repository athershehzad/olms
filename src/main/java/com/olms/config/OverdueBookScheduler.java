package com.olms.config;

import com.olms.entity.StudentBooksEntity;
import com.olms.repository.BookFineRepository;
import com.olms.repository.StudentBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@AllArgsConstructor
public class OverdueBookScheduler {

    private final StudentBooksRepository studentBooksRepository;
    private final BookFineRepository bookFineRepository;

    // */10 * * * * *  run after every 10 seconds
    @Scheduled(cron = "@midnight")
    public void trackOverduePayments() {

        List<StudentBooksEntity> list = studentBooksRepository.findByIssueOnIsGreaterThan();
        LocalDate currentDate = LocalDate.now();
        if (list.size() > 0) {
            Double fineAmount = bookFineRepository.findAllByStatusOrderByIdDesc(true).get(0).getFineAmount();
            for (StudentBooksEntity booksEntity : list) {
                long noOfDaysBetween = ChronoUnit.DAYS.between(booksEntity.getIssueOn(), currentDate);
                long finedDays = noOfDaysBetween - 15;
                booksEntity.setBookFine(finedDays * fineAmount);
                studentBooksRepository.save(booksEntity);
            }
        }

    }

}
