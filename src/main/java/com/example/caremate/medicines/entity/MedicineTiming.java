package com.example.caremate.medicines.entity;

import java.util.Arrays;

public enum MedicineTiming {
    BEFORE_MEAL,
    AFTER_MEAL,
    WITH_MEAL,
    EMPTY_STOMACH,
    ANYTIME;

    public static String[] ALL() {
        return Arrays.stream(MedicineTiming.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}