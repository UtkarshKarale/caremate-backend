package com.example.caremate.doctorInfo.service;

import com.example.caremate.doctorInfo.dto.DoctorInfoDTO;
import com.example.caremate.doctorInfo.entity.DoctorInfo;
import com.example.caremate.doctorInfo.exception.DoctorInfoNotFoundException;
import com.example.caremate.doctorInfo.repository.DoctorInfoRepository;
import com.example.caremate.prescription.exception.DoctorNotFoundException;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorInfoService {

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public DoctorInfo registerDoctorInfo(DoctorInfoDTO dto) {
        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + dto.getDoctorId()));

        DoctorInfo info = new DoctorInfo();
        info.setDoctor(doctor);
        info.setSpecialization(dto.getSpecialization());
        info.setQualifications(dto.getQualifications());
        info.setExperienceInYears(dto.getExperienceInYears());
        info.setConsultationFee(dto.getConsultationFee());
        info.setBio(dto.getBio());

        return doctorInfoRepository.save(info);
    }

    public DoctorInfo getDoctorInfoByDoctorId(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

        return doctorInfoRepository.findByDoctor(doctor)
                .orElseThrow(() -> new DoctorInfoNotFoundException("Doctor info not found for doctor ID: " + doctorId));
    }

    public DoctorInfo updateDoctorInfo(Long doctorId, DoctorInfoDTO dto) {
        DoctorInfo existingInfo = doctorInfoRepository.findByDoctorId(doctorId);

        if (existingInfo == null) {
            throw new DoctorInfoNotFoundException("Doctor info not found for doctor ID: " + doctorId);
        }

        if (dto.getSpecialization() != null) {
            existingInfo.setSpecialization(dto.getSpecialization());
        }
        if (dto.getQualifications() != null) {
            existingInfo.setQualifications(dto.getQualifications());
        }
        if (dto.getExperienceInYears() != null) {
            existingInfo.setExperienceInYears(dto.getExperienceInYears());
        }
        if (dto.getConsultationFee() != null) {
            existingInfo.setConsultationFee(dto.getConsultationFee());
        }
        if (dto.getBio() != null) {
            existingInfo.setBio(dto.getBio());
        }

        return doctorInfoRepository.save(existingInfo);
    }
}