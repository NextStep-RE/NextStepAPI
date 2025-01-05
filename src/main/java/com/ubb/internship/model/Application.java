package com.ubb.internship.model;

import com.ubb.internship.model.enums.ApplicationStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "applications")
@Entity
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_applied")
    private Date dateApplied;

    @Column(name = "application_status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum status;

    @Column(name = "interview_date")
    private Date interviewDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
