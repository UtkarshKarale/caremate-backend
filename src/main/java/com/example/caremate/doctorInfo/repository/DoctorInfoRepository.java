package com.example.caremate.doctorInfo.repository;

import com.example.caremate.doctorInfo.entity.DoctorInfo;
import com.example.caremate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Long> {
    Optional<DoctorInfo> findByDoctor(User doctor);

    DoctorInfo findByDoctorId(Long doctorId);
}
