package com.olms.repository.specification;

import com.olms.dto.StudentDTO;
import com.olms.entity.StudentBooksEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class BookSearchSpecification implements Specification<StudentBooksEntity> {

    private final static String STUDENT_NUMBER = "studentNumber";
    private final static String ISSUE_ON = "issueOn";
    private final static String RECEIVE_ON = "receiveOn";
    private final StudentDTO criteria;

    public BookSearchSpecification(StudentDTO criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<StudentBooksEntity> root, CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        return buildSearchCriteria(root, criteriaBuilder);
    }

    private Predicate buildSearchCriteria(Root<StudentBooksEntity> root, CriteriaBuilder criteriaBuilder) {

        Predicate p = criteriaBuilder.conjunction();

        if (StringUtils.hasText(criteria.getStudentNumber())) {
            p.getExpressions().add(criteriaBuilder.equal(root.get(STUDENT_NUMBER), criteria.getStudentNumber()));
        }

        String toDate;
        String fromDate;
        if (StringUtils.hasText(criteria.getIssueDate()) || StringUtils.hasText(criteria.getReceiveDate())) {
            if (!StringUtils.hasText(criteria.getIssueDate())) {
                toDate = criteria.getReceiveDate();
                p.getExpressions().add(criteriaBuilder.equal(root.get(ISSUE_ON).as(LocalDate.class),
                        LocalDate.parse(toDate)));
            } else if (!StringUtils.hasText(criteria.getReceiveDate())) {
                fromDate = criteria.getIssueDate();
                p.getExpressions().add(criteriaBuilder.equal(root.get(ISSUE_ON).as(LocalDate.class),
                        LocalDate.parse(fromDate)));
            } else {
                toDate = criteria.getReceiveDate();
                fromDate = criteria.getIssueDate();
                p.getExpressions().add(criteriaBuilder.between(root.get(ISSUE_ON).as(LocalDate.class),
                        LocalDate.parse(fromDate), LocalDate.parse(toDate)));
            }
        }

        return p;
    }
}