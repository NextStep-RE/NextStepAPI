package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExperienceDto {

    private Long id;
    private String role;
    private String companyName;
    private Date startDate;
    private Date endDate;

}
