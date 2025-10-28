package com.example.caremate.doctorInfo.controller;

import com.example.caremate.doctorInfo.dto.DoctorInfoDTO;
import com.example.caremate.doctorInfo.entity.DoctorInfo;
import com.example.caremate.doctorInfo.service.DoctorInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DoctorInfo")
@RestController
@RequestMapping("/api/doctor/info")
public class DoctorInfoController {

    @Autowired
    private DoctorInfoService doctorInfoService;

    // ✅ Register Doctor Info
    @PostMapping("/register")
    public DoctorInfo registerDoctorInfo(@RequestBody DoctorInfoDTO dto) {
        return doctorInfoService.registerDoctorInfo(dto);
    }

    @GetMapping("/{doctorId}")
    public DoctorInfo getDoctorInfoByDoctorId(@PathVariable Long doctorId) {
        return doctorInfoService.getDoctorInfoByDoctorId(doctorId);
    }

    @PutMapping("/update/{doctorId}")
    public DoctorInfo updateDoctorInfo(@PathVariable Long doctorId,
                                       @RequestBody DoctorInfoDTO dto) {
        return doctorInfoService.updateDoctorInfo(doctorId, dto);
    }

}
