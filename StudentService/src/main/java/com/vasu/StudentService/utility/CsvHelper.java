package com.vasu.StudentService.utility;

import com.vasu.StudentService.entity.StudentScholarship;
import org.supercsv.io.ICsvBeanWriter;

import java.io.IOException;
import java.util.List;

public class CsvHelper {

    static String[] HEADER_STUDENT_SCHOLARSHIP = {
            "Student Roll Number", "Student Name", "Science Marks", "Maths Marks", "Computer Marks",
            "English Marks", "Is Eligible"
    };

    static String[] NAME_MAPPING_STUDENT_SCHOLARSHIP = {
            "studentRollNumber", "studentName", "scienceMarks", "mathsMarks", "computerMarks",
            "englishMarks", "isEligible"
    };

    public static void generateCsvForStudentScholarship(List<StudentScholarship> students, ICsvBeanWriter csvWriter) throws IOException {
        csvWriter.writeHeader(HEADER_STUDENT_SCHOLARSHIP);

        students.forEach(student -> {
            try {
                csvWriter.write(student, NAME_MAPPING_STUDENT_SCHOLARSHIP);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
