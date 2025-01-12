package com.ubb.internship.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventDto {

    private String title;
    private Date startDate;
    private String location;
    private Long userId;

}
