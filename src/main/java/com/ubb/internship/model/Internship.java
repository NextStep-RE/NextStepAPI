package com.ubb.internship.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "internships")
@Entity
@Getter
@Setter
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "description")
    private String description;

    @Column(name = "experience")
    private String experience; // Optional field

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "application_deadline")
    @Temporal(TemporalType.DATE)
    private Date applicationDeadline;

    @ElementCollection
    @CollectionTable(name = "internship_requirements", joinColumns = @JoinColumn(name = "internship_id"))
    @Column(name = "requirement")
    private List<String> requirements;

    @Column(name = "date_added", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @OneToMany(mappedBy = "internship")
    private List<Application> applications;

    @Override
    public String toString() {
        return "Internship{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company=" + company +
                ", description='" + description + '\'' +
                ", experience='" + experience + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", applicationDeadline=" + applicationDeadline +
                ", requirements=" + requirements +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
