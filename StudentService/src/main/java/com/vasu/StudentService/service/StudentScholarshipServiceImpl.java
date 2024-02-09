package com.vasu.StudentService.service;

import com.vasu.StudentService.entity.StudentScholarship;
import com.vasu.StudentService.model.UpdateMarksRequest;
import com.vasu.StudentService.repository.StudentScholarshipRepository;
import com.vasu.StudentService.utility.CsvHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentScholarshipServiceImpl implements StudentScholarshipService{

    private static final Logger logger = LoggerFactory.getLogger(StudentScholarshipServiceImpl.class);
    private final StudentScholarshipRepository repository;

    @Override
    public void updatedCsv(HttpServletResponse response) throws IOException {
        List<StudentScholarship> students = repository.findAll();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        if (students.isEmpty()) logger.info("Student Details Not Yet Uploaded !");
        else {
            logger.info("Generating CSV for {} students", students.size());
            CsvHelper.generateCsvForStudentScholarship(students, csvWriter);
        }
        csvWriter.close();
    }

    private void updateEligibility(StudentScholarship student) {
        float scienceMarks = student.getScienceMarks();
        float mathsMarks = student.getMathsMarks();
        float computerMarks = student.getComputerMarks();
        float englishMarks = student.getEnglishMarks();

        boolean isEligible = (scienceMarks > 85 && mathsMarks > 90 && computerMarks > 95 && englishMarks > 75);
        student.setIsEligible(isEligible ? "YES" : "NO");
    }

    private void updateMarks(StudentScholarship student, UpdateMarksRequest marksRequest) {
        if (marksRequest.getScienceMarks() != null) student.setScienceMarks(marksRequest.getScienceMarks());
        if (marksRequest.getEnglishMarks() != null) student.setEnglishMarks(marksRequest.getEnglishMarks());
        if (marksRequest.getMathsMarks() != null) student.setMathsMarks(marksRequest.getMathsMarks());
        if (marksRequest.getComputerMarks() != null) student.setComputerMarks(marksRequest.getComputerMarks());
    }

    @Override
    public ResponseEntity<String> updateStudentMarks(UpdateMarksRequest marksRequest, String studentRollNumber) {
        Optional<StudentScholarship> studentOptional = repository.findFirstByStudentRollNumber(studentRollNumber);

        if (studentOptional.isEmpty()) {
            logger.warn("Student not found with roll number: {}", studentRollNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student Not Found: No records match the provided Roll Number.");
        }

        StudentScholarship student = studentOptional.get();
        updateMarks(student, marksRequest);
        updateEligibility(student);

        repository.save(student);
        logger.info("Marks updated successfully for student with roll number: {}", studentRollNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body("The student's marks have been successfully updated.");
    }

    @Override
    public ResponseEntity<Map<String, String>> searchStudent(String studentRollNumber) {
        Optional<StudentScholarship> studentOptional = repository.findFirstByStudentRollNumber(studentRollNumber);

        Map<String, String> response = new HashMap<>();

        if (studentOptional.isEmpty()) {
            logger.warn("Student not found with roll number: {}", studentRollNumber);
            response.put("Eligibility", "NA");
            response.put("Message", "Student Not Found: No records match the provided Roll Number.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        StudentScholarship student = studentOptional.get();
        response.put("Eligibility", student.getIsEligible());
        logger.info("Student found with roll number: {}. Eligibility: {}", studentRollNumber, student.getIsEligible());
        return ResponseEntity.ok(response);
    }

}
