package com.example.androidproject.core.utils;

public class MoneyFomat {
    public static String format(double money) {
        return String.format("%,.0f", money);
    }
}
