package com.example.hospital_management.framework.model;

import java.util.Arrays;

public enum UserRoles {
    USER, ADMIN, DOCTOR, PATIENT, RECEPTIONIST ;

    public static String[] ALL() {
        return Arrays.stream(UserRoles.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
