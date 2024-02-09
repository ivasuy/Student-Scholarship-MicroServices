package com.vasu.StudentService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateMarksRequest {
    private Float scienceMarks;
    private Float mathsMarks;
    private Float computerMarks;
    private Float englishMarks;
}
