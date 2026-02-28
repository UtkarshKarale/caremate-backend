package com.example.caremate.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalDoctorDTO {
    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private String specialist;
    private String location;
}
