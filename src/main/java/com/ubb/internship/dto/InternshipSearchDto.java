package com.ubb.internship.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InternshipSearchDto {
    private String title;
    private String companyName;
    private String location;
    private Date startDate;
    private Date endDate;
    private Date applicationDeadline;
    private List<String> requirements;
    private String sortBy;
    private boolean ascending;

}

