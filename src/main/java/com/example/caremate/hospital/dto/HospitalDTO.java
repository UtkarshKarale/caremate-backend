package com.example.caremate.hospital.dto;

import com.example.caremate.hospital.entity.Hospital;
import com.example.caremate.hospital.entity.HospitalType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalDTO {

    private Integer id;
    private String hospitalName;
    private String specialization;
    private String address;
    private String mobileNo;
    private String email;
    private String website;
    private Integer totalBeds;
    private HospitalType hospitalType;
    private boolean isActive;
}