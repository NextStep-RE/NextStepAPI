package com.ubb.internship.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EducationRequestDto {

    private String degree;
    private String major;
    private String university;
    private Date startDate;
    private Date endDate;
    private Long userId;

}
