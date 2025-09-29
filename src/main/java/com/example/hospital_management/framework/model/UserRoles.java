package com.example.hospital_management.framework.model;

import java.util.Arrays;

public enum UserRoles {
    ROLE_USER, ROLE_ADMIN, ROLE_DOCTOR, ROLE_PATIENT, ROLE_RECEPTIONIST ;

    public static String[] ALL() {
        return Arrays.stream(UserRoles.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
