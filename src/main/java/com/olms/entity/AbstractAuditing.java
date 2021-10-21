package com.olms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractAuditing implements Serializable {

    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "updated_on")
    private LocalDateTime updatedDate;

}
