package com.ubb.internship.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "companies")
@Entity
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website; // Optional

    @Column(name = "logo_url")
    private String logoUrl; // Optional

    @Column(name = "industry")
    private String industry; // Optional, for the company's sector

    @Column(name = "date_joined", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJoined;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", industry='" + industry + '\'' +
                ", dateJoined=" + dateJoined +
                '}';
    }
}
