package com.example.androidproject.features.admin_manager.data.datasource;

import com.example.androidproject.features.admin_manager.data.model.CouponModel;

import java.util.Arrays;
import java.util.List;

public class DummyCouponData {

    public static List<CouponModel> generateDummyCoupons() {
        return Arrays.asList(
                createCoupon("C001", "Discount10", 50, "percentage", 10.0, 0.0, "2024-10-28 08:00", "2024-10-28 23:59"),
                createCoupon("C002", "Discount20", 30, "percentage", 20.0, 0.0, "2024-11-01 00:00", "2024-11-30 23:59"),
                createCoupon("C003", "TECHNOWITHYOU", 0, "fixed", 50.0, 150.0, "2024-09-01 00:00", "2024-09-30 23:59"),
                createCoupon("C004", "EarlyBird", 0, "fixed", 100.0, 250.0, "2024-12-01 00:00", "2024-12-31 23:59"),
                createCoupon("C005", "SpecialDiscount", 0, "percentage", 15.0, 0.0, "2024-10-28 08:00", "2024-10-28 23:59")
        );
    }

    private static CouponModel createCoupon(String couponId, String name, int quantity, String type,
                                            double value, double minimalTotal, String dateStart, String dateEnd) {
        return new CouponModel(couponId, name, quantity, type, value, minimalTotal, dateStart, dateEnd);
    }
}
