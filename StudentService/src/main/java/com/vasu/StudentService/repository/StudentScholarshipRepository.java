package com.vasu.StudentService.repository;

import com.vasu.StudentService.entity.StudentScholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentScholarshipRepository extends JpaRepository<StudentScholarship, Long> {
    Optional<StudentScholarship> findFirstByStudentRollNumber(String studentRollNumber);
}
