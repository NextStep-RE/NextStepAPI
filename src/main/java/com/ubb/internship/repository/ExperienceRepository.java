package com.ubb.internship.repository;

import com.ubb.internship.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByUserId(Long userId);
}
