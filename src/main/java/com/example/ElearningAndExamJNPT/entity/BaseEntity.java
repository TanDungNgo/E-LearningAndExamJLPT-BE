package com.example.ElearningAndExamJNPT.entity;

import com.example.ElearningAndExamJNPT.entity.User.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @CreatedBy
    private String createdBy;
    @Column
    @CreatedDate
    private Date createdDate;

    @Column
    @LastModifiedBy
    private String modifiedBy;
    @Column
    @LastModifiedDate
    private Date modifiedDate;

}

