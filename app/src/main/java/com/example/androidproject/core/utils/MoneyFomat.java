package com.example.androidproject.core.utils;

public class MoneyFomat {
    public static String format(double money) {
        return String.format("%,.0f", money);
    }

    public static double parseMoney(String formattedMoney) {
        try {
            return Double.parseDouble(formattedMoney.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sai định dạng: " + formattedMoney, e);
        }
    }
}
