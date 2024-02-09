package com.vasu.StudentService.service;

import com.vasu.StudentService.model.UpdateMarksRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface StudentScholarshipService {

    void updatedCsv(HttpServletResponse response) throws IOException;

    ResponseEntity<String> updateStudentMarks(UpdateMarksRequest marksRequest, String studentRollNumber);

    ResponseEntity<Map<String, String>> searchStudent(String studentRollNumber);
}
