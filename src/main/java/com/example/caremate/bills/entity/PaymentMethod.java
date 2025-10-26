package com.example.caremate.bills.entity;

import java.util.Arrays;

public enum PaymentMethod {
    CASH,
    CARD,
    UPI,
    NET_BANKING,
    INSURANCE,
    CHEQUE;

    public static String[] ALL() {
        return Arrays.stream(PaymentMethod.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}