package com.vasu.StudentService.config;

import com.vasu.StudentService.entity.StudentScholarship;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<StudentScholarship, StudentScholarship> {

    @Override
    public StudentScholarship process(StudentScholarship student) throws Exception {
        try {
            Float scienceMarks = student.getScienceMarks();
            Float mathsMarks = student.getMathsMarks();
            Float computerMarks = student.getComputerMarks();
            Float englishMarks = student.getEnglishMarks();

            boolean isEligible = (scienceMarks > 85 && mathsMarks > 90 && computerMarks > 95 && englishMarks > 75);

            String eligibility = isEligible ? "YES" : "NO";
            student.setIsEligible(eligibility);
        } catch (NumberFormatException ex) {ex.printStackTrace();}

        return student;
    }
}
