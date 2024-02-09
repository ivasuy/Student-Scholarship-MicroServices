package com.vasu.StudentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "STUDENT_SCHOLARSHIP_TABLE")
public class StudentScholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "STUDENT_ROLL_NUMBER")
    private String studentRollNumber;

    @Column(name = "STUDENT_NAME")
    private String studentName;

    @Column(name = "SCIENCE_MARKS")
    private Float scienceMarks;

    @Column(name = "MATHS_MARKS")
    private Float mathsMarks;

    @Column(name = "COMPUTER_MARKS")
    private Float computerMarks;

    @Column(name = "ENGLISH_MARKS")
    private Float englishMarks;

    @Column(name = "IS_ELIGIBLE")
    private String isEligible;
}
