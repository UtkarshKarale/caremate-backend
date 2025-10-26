package com.example.caremate.bills.entity;

import java.util.Arrays;

public enum BillStatus {
    PENDING,
    PAID,
    PARTIALLY_PAID,
    CANCELLED;

    public static String[] ALL() {
        return Arrays.stream(BillStatus.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}