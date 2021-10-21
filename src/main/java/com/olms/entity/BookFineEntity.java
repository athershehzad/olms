package com.olms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "book_fine")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
public class BookFineEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "fine_amount", nullable = false)
    private Double fineAmount;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "fine_created_on")
    private LocalDate createdOn;

    @Column(name = "fine_updated_on")
    private LocalDate updatedOn;
}


