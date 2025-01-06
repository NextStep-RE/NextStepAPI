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
<<<<<<< Updated upstream
    private Long companyId;
=======
    private String companyName;
    private String companyLogoUrl;
    private String companyWebsite;
>>>>>>> Stashed changes
    private String description;
    private String experience;
    private String location;
    private Date startDate;
    private Date endDate;
    private Date applicationDeadline;
    private List<String> requirements;

}
