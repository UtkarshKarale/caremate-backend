package com.example.caremate.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospitals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String specialization;   // e.g. Cardiology, Orthopedics

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 15)
    private String mobileNo;

    private String email;

    private String website;

    private Integer totalBeds;

    @Enumerated(EnumType.STRING)
    private HospitalType hospitalType;  // GOVERNMENT, PRIVATE, TRUST

    private boolean isActive = true;


}