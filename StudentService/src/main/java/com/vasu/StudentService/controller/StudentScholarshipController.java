package com.vasu.StudentService.controller;

import com.vasu.StudentService.model.UpdateMarksRequest;
import com.vasu.StudentService.service.StudentScholarshipService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StudentScholarshipController {

    @Value("${temp.storage.directory}")
    private String tempStorageDirectory;
    private final JobLauncher jobLauncher;
    private final Job job;
    private final StudentScholarshipService service;

    @PostMapping("/v1/student-scholarship/upload-csv")
    public void uploadStudentCSV(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        try {
            String originalFileName = file.getOriginalFilename();
            File fileToImport = new File(tempStorageDirectory + originalFileName);
            file.transferTo(fileToImport);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", tempStorageDirectory + originalFileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {e.printStackTrace();}

        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=student_" + "scholarship" + ".csv";
        response.setHeader(headerKey, headerValue);

        service.updatedCsv(response);
    }

    @PostMapping("/v1/student-scholarship/update-marks")
    public ResponseEntity<String> updateStudentMarks(@RequestBody UpdateMarksRequest marksRequest,
                                                     @RequestParam String studentRollNumber){
        return service.updateStudentMarks(marksRequest, studentRollNumber);
    }

    @PostMapping("/v1/student-scholarship/search-student")
    public ResponseEntity<Map<String,String>> searchStudent(@RequestParam String studentRollNumber){
        return service.searchStudent(studentRollNumber);
    }

}
