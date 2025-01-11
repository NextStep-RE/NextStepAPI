package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationDto {

    private Long id;
    private Date dateApplied;
    private String status;
    private Date interviewDate;
    private InternshipDto internship;

}
