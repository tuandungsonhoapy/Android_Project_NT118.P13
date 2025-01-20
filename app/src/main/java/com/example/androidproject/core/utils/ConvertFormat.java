package com.example.androidproject.core.utils;

import java.text.DecimalFormat;

public class ConvertFormat {
    public static String formatPriceToVND(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(price) + "đ"; // Thêm ký hiệu tiền tệ
    }
}
