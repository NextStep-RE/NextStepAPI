package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InternshipListDto {
    private Integer totalNumber;
    private List<InternshipDto> internships;
}
