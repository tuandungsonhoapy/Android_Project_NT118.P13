package com.example.androidproject.features.admin_manager.data.model;

import com.example.androidproject.features.admin_manager.data.entity.CouponEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class CouponModel extends CouponEntity {

    private static final TimeZone VIETNAM_TIMEZONE = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

    public CouponModel() {
    }

    public CouponModel(String couponId, String name, int quantity, String type, double value, double minimalTotal, String dateStart, String dateEnd) {
        super(couponId, name, quantity, type, value, minimalTotal, dateStart, dateEnd);
    }

    public static List<CouponEntity> toCouponEntity(List<CouponModel> items) {
        return items.stream()
                .map(item -> new CouponEntity(item.getCouponId(), item.getName(), item.getQuantity(), item.getType(), item.getValue(), item.getMinimalTotal(), item.getDateStart(), item.getDateEnd()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CouponModel{" +
                "couponId='" + getCouponId() + '\'' +
                ", name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", type='" + getType() + '\'' +
                ", value=" + getValue() +
                ", minimalTotal=" + getMinimalTotal() +
                ", dateStart='" + getDateStart() + '\'' +
                ", dateEnd='" + getDateEnd() + '\'' +
                ", status='" + checkStatus() + '\'' +
                '}';
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        sdf.setTimeZone(VIETNAM_TIMEZONE);
        return sdf.parse(date);
    }

    public boolean isActive() {
        try {
            Date start = parseDate(getDateStart());
            Date end = parseDate(getDateEnd());
            Date now = Calendar.getInstance(VIETNAM_TIMEZONE).getTime();
            return getQuantity() > 0 && now.after(start) && now.before(end);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUpcoming() {
        try {
            Date start = parseDate(getDateStart());
            Date now = Calendar.getInstance(VIETNAM_TIMEZONE).getTime();
            return now.before(start);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isExpired() {
        try {
            Date end = parseDate(getDateEnd());
            Date now = Calendar.getInstance(VIETNAM_TIMEZONE).getTime();
            return getQuantity() <= 0 || now.after(end);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String checkStatus() {
        if (isExpired()) {
            return "Expired";
        } else if (isUpcoming()) {
            return "Upcoming";
        } else if (isActive()) {
            return "Active";
        } else {
            return "Invalid Date";
        }
    }
}
