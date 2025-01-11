package com.ubb.internship.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExperienceRequestDto {

    private String role;
    private Date startDate;
    private Date endDate;
    private String companyName;
    private Long userId;

}
