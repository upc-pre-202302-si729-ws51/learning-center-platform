package com.acme.learning.platform.shared.domain.model.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Data
public class AuditableModel {

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

}
