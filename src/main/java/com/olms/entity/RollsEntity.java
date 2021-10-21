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
@Table(name = "rolls")
@EntityListeners(AuditingEntityListener.class)
public class RollsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "roll_name", nullable = true)
    private String rollName;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_by", nullable = false)
    @CreatedDate
    private Date createdBy;

    @Column(name = "updated_by", nullable = false)
    @CreatedBy
    private String updatedBy;

}
