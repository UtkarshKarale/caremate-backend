package com.example.caremate.hospital.service;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.hospital.dto.HospitalDTO;
import com.example.caremate.hospital.dto.HospitalDoctorDTO;
import com.example.caremate.hospital.entity.Hospital;
import com.example.caremate.hospital.repository.HospitalRepository;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;

    @Transactional
    public HospitalDTO createHospital(HospitalDTO dto) {
        Hospital hospital = toEntity(dto);
        return toDTO(hospitalRepository.save(hospital));
    }

    @Transactional(readOnly = true)
    public List<HospitalDTO> getAllHospitals() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HospitalDTO getHospitalById(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
        return toDTO(hospital);
    }

    @Transactional(readOnly = true)
    public List<HospitalDTO> getHospitalsBySpecialization(String specialization) {
        return hospitalRepository
                .findBySpecializationIgnoreCaseAndIsActiveTrue(specialization)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
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

    @Transactional
    public void deleteHospital(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital not found with id: " + id);
        }
        hospitalRepository.deleteById(id);
    }

    @Transactional
    public HospitalDTO assignDoctor(Integer hospitalId, Long doctorId) {
        Hospital hospital = getHospitalEntityById(hospitalId);
        User doctor = getDoctorById(doctorId);

        hospital.getDoctors().add(doctor);
        return toDTO(hospitalRepository.save(hospital));
    }

    @Transactional
    public HospitalDTO unassignDoctor(Integer hospitalId, Long doctorId) {
        Hospital hospital = getHospitalEntityById(hospitalId);
        User doctor = getDoctorById(doctorId);

        hospital.getDoctors().remove(doctor);
        return toDTO(hospitalRepository.save(hospital));
    }

    @Transactional
    public HospitalDTO replaceAssignedDoctors(Integer hospitalId, List<Long> doctorIds) {
        Hospital hospital = getHospitalEntityById(hospitalId);

        Set<User> doctors = (doctorIds == null ? Collections.<Long>emptyList() : doctorIds)
                .stream()
                .map(this::getDoctorById)
                .collect(Collectors.toSet());

        hospital.setDoctors(doctors);
        return toDTO(hospitalRepository.save(hospital));
    }

    @Transactional(readOnly = true)
    public List<HospitalDoctorDTO> getAvailableDoctors() {
        return userRepository.findByRoles(UserRoles.DOCTOR)
                .stream()
                .map(this::toDoctorDTO)
                .collect(Collectors.toList());
    }

    private Hospital getHospitalEntityById(Integer id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    private User getDoctorById(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        if (doctor.getRoles() != UserRoles.DOCTOR) {
            throw new RuntimeException("User is not a doctor with id: " + doctorId);
        }

        return doctor;
    }

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

    private HospitalDTO toDTO(Hospital hospital) {
        return HospitalDTO.builder()
                .id(hospital.getId())
                .hospitalName(hospital.getHospitalName())
                .specialization(hospital.getSpecialization())
                .address(hospital.getAddress())
                .mobileNo(hospital.getMobileNo())
                .email(hospital.getEmail())
                .website(hospital.getWebsite())
                .totalBeds(hospital.getTotalBeds())
                .hospitalType(hospital.getHospitalType())
                .isActive(hospital.isActive())
                .assignedDoctors(hospital.getDoctors().stream().map(this::toDoctorDTO).collect(Collectors.toList()))
                .build();
    }

    private HospitalDoctorDTO toDoctorDTO(User doctor) {
        return HospitalDoctorDTO.builder()
                .id(doctor.getId())
                .fullName(doctor.getFullName())
                .email(doctor.getEmail())
                .mobile(doctor.getMobile())
                .specialist(doctor.getSpecialist())
                .location(doctor.getLocation())
                .build();
    }
}
