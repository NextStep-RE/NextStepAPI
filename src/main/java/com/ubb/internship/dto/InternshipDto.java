package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InternshipDto {
    private Long id;
    private String title;
    private CompanyDto company;
    private String description;
    private String experience;
    private String location;
    private Date startDate;
    private Date endDate;
    private Date applicationDeadline;
    private List<String> requirements;

}
