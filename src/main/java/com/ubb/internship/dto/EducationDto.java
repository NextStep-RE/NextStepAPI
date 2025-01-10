package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EducationDto {

    private Long id;
    private String degree;
    private String major;
    private String university;
    private Date startDate;
    private Date endDate;

}
