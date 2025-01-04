package com.ubb.internship.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InternshipRequestDto {
    private Long id;
    private String title;
    private Long companyId;
    private String description;
    private String experience;
    private String location;
    private Date startDate;
    private Date endDate;
    private Date applicationDeadline;
    private List<String> requirements;
}
