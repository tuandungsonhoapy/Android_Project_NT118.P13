package com.example.androidproject.core.utils;

import java.text.NumberFormat;
import java.util.Locale;

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

    //tuantuton
    public static String formatToCurrency(long amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return numberFormat.format(amount);
    }
}
