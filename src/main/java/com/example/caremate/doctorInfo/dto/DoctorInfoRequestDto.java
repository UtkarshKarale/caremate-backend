package com.example.caremate.doctorInfo.dto;

import lombok.Data;

@Data
public class DoctorInfoRequestDto {
    private Integer doctorId;
    private String specialization;
    private String qualifications;
    private Integer experienceInYears;
    private Double consultationFee;
    private String bio;
}
