package com.example.caremate.bills.entity;

import java.util.Arrays;

public enum ItemType {
    CONSULTATION,
    MEDICINE,
    LAB_TEST,
    PROCEDURE,
    ROOM_CHARGE,
    SURGERY,
    OTHER;

    public static String[] ALL() {
        return Arrays.stream(ItemType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}