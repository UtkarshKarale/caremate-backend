package com.example.caremate.hospital.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignDoctorsRequest {
    private List<Long> doctorIds;
}
