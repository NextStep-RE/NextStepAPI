package com.ubb.internship.model;

import com.ubb.internship.model.enums.DegreeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "educations")
@Entity
@Getter
@Setter
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "degree")
    @Enumerated(EnumType.STRING)
    private DegreeEnum degree;

    @Column(name = "major")
    private String major;

    @Column(name = "university")
    private String university;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
