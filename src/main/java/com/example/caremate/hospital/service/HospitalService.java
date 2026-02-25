package com.example.caremate.hospital.service;

import com.example.caremate.hospital.dto.HospitalDTO;
import com.example.caremate.hospital.entity.Hospital;
import com.example.caremate.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    // ─── CREATE ───────────────────────────────────────────────
    public HospitalDTO createHospital(HospitalDTO dto) {
        Hospital hospital = toEntity(dto);
        return toDTO(hospitalRepository.save(hospital));
    }

    // ─── READ ALL ─────────────────────────────────────────────
    public List<HospitalDTO> getAllHospitals() {
        return hospitalRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ─── READ BY ID ───────────────────────────────────────────
    public HospitalDTO getHospitalById(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
        return toDTO(hospital);
    }

    // ─── GET BY SPECIALIZATION ────────────────────────────────
    public List<HospitalDTO> getHospitalsBySpecialization(String specialization) {
        return hospitalRepository
                .findBySpecializationIgnoreCaseAndIsActiveTrue(specialization)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ─── UPDATE ───────────────────────────────────────────────
    public HospitalDTO updateHospital(Integer id, HospitalDTO dto) {
        Hospital existing = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

        existing.setHospitalName(dto.getHospitalName());
        existing.setSpecialization(dto.getSpecialization());
        existing.setAddress(dto.getAddress());
        existing.setMobileNo(dto.getMobileNo());
        existing.setEmail(dto.getEmail());
        existing.setWebsite(dto.getWebsite());
        existing.setTotalBeds(dto.getTotalBeds());
        existing.setHospitalType(dto.getHospitalType());
        existing.setActive(dto.isActive());

        return toDTO(hospitalRepository.save(existing));
    }

    // ─── DELETE ───────────────────────────────────────────────
    public void deleteHospital(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital not found with id: " + id);
        }
        hospitalRepository.deleteById(id);
    }

    // ─── MAPPERS ──────────────────────────────────────────────
    private Hospital toEntity(HospitalDTO dto) {
        return Hospital.builder()
                .id(dto.getId())
                .hospitalName(dto.getHospitalName())
                .specialization(dto.getSpecialization())
                .address(dto.getAddress())
                .mobileNo(dto.getMobileNo())
                .email(dto.getEmail())
                .website(dto.getWebsite())
                .totalBeds(dto.getTotalBeds())
                .hospitalType(dto.getHospitalType())
                .isActive(dto.isActive())
                .build();
    }

    private HospitalDTO toDTO(Hospital h) {
        return HospitalDTO.builder()
                .id(h.getId())
                .hospitalName(h.getHospitalName())
                .specialization(h.getSpecialization())
                .address(h.getAddress())
                .mobileNo(h.getMobileNo())
                .email(h.getEmail())
                .website(h.getWebsite())
                .totalBeds(h.getTotalBeds())
                .hospitalType(h.getHospitalType())
                .isActive(h.isActive())
                .build();
    }
}